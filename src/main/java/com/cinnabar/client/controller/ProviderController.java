package com.cinnabar.client.controller;

import com.cinnabar.client.beans.User;
import com.cinnabar.client.config.Logback;
import com.cinnabar.client.config.authToken.AuthToken;
import com.cinnabar.client.config.handelResponse.ResponseCtrl;
import com.cinnabar.client.config.handelResponse.ResponseTemplate;
import com.cinnabar.client.config.redisHelper.RedisHelper;
import com.cinnabar.client.mapper.UserMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Api(tags = "用户接口", produces = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping("/user/")
public class ProviderController {

    @Autowired
    UserMapper userMapper;

    @Autowired
    Logback logback;

    @Autowired
    RedisHelper redisHelper;

    @ApiOperation(value = "return a url parameter")
    @PostMapping(value = "/{name}")
    public ResponseTemplate<User> findName(@PathVariable("name") String name, @RequestParam("age") Integer age, @RequestBody User user) {
        return ResponseCtrl.in((result) -> {
            System.out.println(logback);
            if ("zhuzhao".equals(name)) {
                throw new Exception("can can can" + age);
            } else {
                User user1 = userMapper.getByUserAccount(user.getAccount());
                result.setData(user1);
            }
        });
    }

    @ApiOperation(value = "userInfo")
    @RequestMapping(value = "userInfo/{token}", method = RequestMethod.GET)
    public ResponseTemplate<String> userInfo(@PathVariable("token") String token) {
        return ResponseCtrl.in((result) -> result.setData(
                RedisHelper.get(token)
        ));
    }

    @AuthToken
    @ApiOperation(value = "form-data")
    @RequestMapping(value = "/formData", method = RequestMethod.POST)
    public ResponseTemplate formData(@RequestPart("file") MultipartFile file, @RequestPart("form") String s) {
        return ResponseCtrl.in((r) -> {
            System.out.println(s);
        });
    }

    @ApiOperation(value = "userRelations")
    @RequestMapping(value = "userRelations/{userId}", method = RequestMethod.GET)
    public ResponseTemplate<List<User>> userRelations(@PathVariable("userId") Integer userId) {
        return ResponseCtrl.in((result) -> result.setData(
                userMapper.getUserRelations(userId)
        ));
    }

}