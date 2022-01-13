package com.cinnabar.client.service;

import com.cinnabar.client.beans.Message;
import com.cinnabar.client.beans.User;
import com.cinnabar.client.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * @author cinnabar-1
 * @version 1.0.0
 * @ClassName MessageServiceTest.java
 * @Description
 * @createTime 2021-07-13  14:37:00
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableTransactionManagement(proxyTargetClass = true)
class MessageServiceTest {
    @Resource
    MessageService messageService;

    @Resource
    UserMapper userMapper;

    @Test
    void saveDelayMessage() {
        messageService.saveDelayMessage(Arrays.asList(new Message()));
    }

    @Test
    void getUserInfo() {
//        UserMapper userMapper = SpringBeans.getBean(UserMapper.class);
        UserMapper enhanceUser = (UserMapper) Proxy.newProxyInstance(userMapper.getClass().getClassLoader(), userMapper.getClass().getInterfaces(),
                (Object proxy, Method method, Object[] args) -> {
                    System.out.println("invocation getByUserAccount");
                    return method.invoke(userMapper, args);
                });
        User user = userMapper.getByUserAccount("password");
        System.out.println(user);
        System.out.println(user.getId());
    }
}