package com.cinnabar.client.service;

import com.cinnabar.client.beans.User;

import java.util.List;

/**
 * @author cinnabar-1
 * @version 1.0.0
 * @ClassName UserService.java
 * @Description
 * @createTime 2021-07-08  13:24:00
 */
public interface UserService {
    /**
     *返回重复的user
     */
    List<User> insertUser(List<User> users);

    List<User> getUserRelationsList(Integer userId);
}
