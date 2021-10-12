package com.cinnabar.client.service.impl;

import com.cinnabar.client.beans.User;
import com.cinnabar.client.config.redisHelper.RedisHelper;
import com.cinnabar.client.mapper.UserMapper;
import com.cinnabar.client.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional
public class UserServiceImpl implements UserService {
    @Resource
    UserMapper userMapper;

    @Override
    public List<User> insertUser(List<User> users) {
        List<String> accounts = users.stream().map(User::getAccount).collect(Collectors.toList());
        List<User> duplicateUser = userMapper.DuplicateUser(accounts);
        users = users.stream().filter(item -> !duplicateUser.contains(item)).collect(Collectors.toList());
        userMapper.insertIntoUser(users);
        return duplicateUser;
    }

    @Override
    public List<User> getUserRelationsList(Integer userId) {
        return userMapper.getUserRelations(userId);
    }

    @Override
    public User getUserByToken(String token) {
        String account = RedisHelper.get(token);
        return userMapper.getByUserAccount(account);
    }
}
