package com.cinnabar.client.config.redisHelper;

import com.cinnabar.client.config.handelException.CommonException;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;

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

    @Autowired
    RedisHelper redisHelper;

    @Test
    void setKeyValue() throws CommonException {
        String key = "test001";
        String value = "test001";
        redisHelper.pipSetKeyValue(key, value, 60);
        redisHelper.get(key);
        redisHelper.getValue(Collections.singletonList(key));
    }

    @Test
    void getValue() {
    }
}