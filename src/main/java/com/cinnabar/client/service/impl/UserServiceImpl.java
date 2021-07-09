package com.cinnabar.client.service.impl;

import com.cinnabar.client.beans.User;
import com.cinnabar.client.mapper.UserMapper;
import com.cinnabar.client.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author cinnabar-1
 * @version 1.0.0
 * @ClassName UserServiceImpl.java
 * @Description
 * @createTime 2021-07-08  13:24:00
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserMapper userMapper;

    @Override
    public Integer insertUser(List<User> users) {
        List<String> accounts = users.stream().map(User::getAccount).collect(Collectors.toList());

        return userMapper.insertIntoUser(users);
    }
}