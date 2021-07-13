package com.cinnabar.client.config.authToken;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author cinnabar-1
 * @version 1.0.0
 * @ClassName Md5TokenGeneratorTest.java
 * @Description
 * @createTime 2021-07-13  14:17:00
 */
class Md5TokenGeneratorTest {

    @Test
    void generate() {
        Md5TokenGenerator tokenGenerator = new Md5TokenGenerator();
        System.out.println(tokenGenerator.generate("123","123"));
        System.out.println(tokenGenerator.generate("123","123"));
    }
}