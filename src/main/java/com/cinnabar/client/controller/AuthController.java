package com.cinnabar.client.controller;

import com.alibaba.fastjson.JSONObject;
import com.cinnabar.client.beans.User;
import com.cinnabar.client.config.CommonStatic;
import com.cinnabar.client.config.authToken.AuthToken;
import com.cinnabar.client.config.authToken.Md5TokenGenerator;
import com.cinnabar.client.config.handelException.CommonException;
import com.cinnabar.client.config.handelResponse.ResponseCtrl;
import com.cinnabar.client.config.handelResponse.ResponseTemplate;
import com.cinnabar.client.config.redisHelper.RedisHelper;
import com.cinnabar.client.mapper.UserMapper;
import com.cinnabar.client.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import java.util.LinkedList;
import java.util.List;

@RestController()
public class AuthController {
    Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private Md5TokenGenerator tokenGenerator;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @GetMapping("/welcome")
    @AuthToken
    public ResponseTemplate<String> Welcome() {
        return ResponseCtrl.in(response -> {
            response.setData("welcome");
        });
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ApiOperation(value = "login")
    @AuthToken
    public ResponseTemplate<JSONObject> Login(String account, String password) {
        return ResponseCtrl.in(result -> {
            logger.info("用户名username为:" + account + "密码password为:" + password);
            User user = userMapper.getByUserAccount(account);
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

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ApiOperation(value = "测试")
    public ResponseTemplate test() {
        logger.info("**************测试start**************");
        return ResponseCtrl.in(r -> r.setData("success"));
    }

    @ApiOperation(value = "signIn")
    @PostMapping("/signIn")
    @AuthToken
    public ResponseTemplate sign(@RequestBody List<User> users) {
        return ResponseCtrl.in(r -> userService.insertUser(users));
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
    private String SetRedisData(String account, String password) throws CommonException {
        String token = tokenGenerator.generate(account, password);
        // 将老的token key移除
        String oldToken = RedisHelper.get(account);
        Jedis jedis = null;
        if (oldToken != null)
            if ((jedis = RedisHelper.getJedis()) != null)
            {
                jedis.del(oldToken);
                jedis.close();
            }
        List<RedisHelper.HelperSet> helperSets = new LinkedList<>();
        //将token和username以键值对的形式存入到redis中进行双向绑定
        //设置key过期时间，到期会自动删除
        helperSets.add(new RedisHelper.HelperSet(account, token, CommonStatic.TOKEN_EXPIRE_TIME));
        helperSets.add(new RedisHelper.HelperSet(token, account, CommonStatic.TOKEN_EXPIRE_TIME));
        RedisHelper.pipSetKeyValue(helperSets);
        return token;
    }
}
