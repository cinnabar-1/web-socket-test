package com.cinnabar.client.config.redisHelper;

import com.cinnabar.client.config.handelException.CommonException;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Response;

import java.util.*;

/**
 * @author cinnabar-1
 * @version 1.0.0
 * @ClassName RedisHelperTest.java
 * @Description
 * @createTime 2021-07-12  13:26:00
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@RunWith(SpringRunner.class)
//@WebAppConfiguration
class RedisHelperTest {

//    @Autowired
//    RedisHelper redisHelper;

    @Test
    void setKeyValue() throws CommonException {
        String key = "test001";
        String value = "test001";
        RedisHelper.set(key, value, 60);
        RedisHelper.set(key, "123", 60);
        System.out.println(RedisHelper.get(key));
        Map<String, Response<String>> map = RedisHelper.pipGetValue(Collections.singletonList(key));
        for (String s :
                map.keySet()) {
            System.out.println(map.get(s).get());
        }
    }

    @Test
    void getValue() {
    }
}