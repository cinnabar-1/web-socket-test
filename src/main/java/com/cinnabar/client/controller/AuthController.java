package com.cinnabar.client.controller;

import com.alibaba.fastjson.JSONObject;
import com.cinnabar.client.beans.User;
import com.cinnabar.client.config.authToken.AuthToken;
import com.cinnabar.client.config.CommonStatic;
import com.cinnabar.client.config.authToken.Md5TokenGenerator;
import com.cinnabar.client.config.handelResponse.ResponseCtrl;
import com.cinnabar.client.config.handelResponse.ResponseTemplate;
import com.cinnabar.client.mapper.UserMapper;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import java.util.List;

@RestController()
public class AuthController {
    Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private Md5TokenGenerator tokenGenerator;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/welcome")
    public String Welcome() {

        return "welcome TokenController ";
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ApiOperation(value = "login")
    public ResponseCtrl.Template Login(String account, String password) {
        return ResponseCtrl.in(result -> {
            logger.info("用户名username为:" + account + "密码password为:" + password);
            User user = userMapper.getByUserAccount(account); // todo 数据库查询
//            User user = new User();
            logger.info("从数据库查出来的用户user为:" + user);

            JSONObject obj = new JSONObject();

            if (user != null && user.getPassword().equals(password)) {
                String token = SetRedisData(user.getAccount(), user.getPassword());
                obj.put("message", "用户登录成功");
                obj.put("token", token);
            } else {
                obj.put("status", "用户登录失败");
            }
            result.setData(obj);
        });
    }

    @RequestMapping(value = "test", method = RequestMethod.GET)
    @AuthToken
    @ApiOperation(value = "测试")
    public ResponseTemplate test() {
        logger.info("**************测试start**************");

        return ResponseTemplate.builder().code(200).message("测试成功").data("测试数据").build();
    }

    @ApiOperation(value = "signIn")
    @PostMapping("/signIn")
    public ResponseCtrl.Template sign(@RequestBody List<User> users) {
        return ResponseCtrl.in(r -> userMapper.insertIntoUser(users));
    }

    /**
     * 在redis中进行数据的绑定
     *
     * @param account
     * @param password
     * @return
     * @Title: SetRedisData
     * @Description:
     * @author
     */
    private String SetRedisData(String account, String password) {
        //此处主要设置redis的ip和端口号
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        String token = tokenGenerator.generate(account, password);
        jedis.set(account, token);
        //设置key过期时间，到期会自动删除
        jedis.expire(account, CommonStatic.TOKEN_EXPIRE_TIME);
        //将token和username以键值对的形式存入到redis中进行双向绑定
        jedis.set(token, account);
        jedis.expire(token, CommonStatic.TOKEN_EXPIRE_TIME);
        long currentTime = System.currentTimeMillis();
        jedis.set(token + account, Long.toString(currentTime));
        //用完关闭
        jedis.close();
        return token;
    }
}
