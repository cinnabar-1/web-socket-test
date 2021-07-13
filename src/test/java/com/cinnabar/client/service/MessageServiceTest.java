package com.cinnabar.client.service;

import com.cinnabar.client.beans.Message;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author cinnabar-1
 * @version 1.0.0
 * @ClassName MessageServiceTest.java
 * @Description
 * @createTime 2021-07-13  14:37:00
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MessageServiceTest {
    @Resource
    MessageService messageService;

    @Test
    void saveDelayMessage() {
        messageService.saveDelayMessage(Arrays.asList(new Message()));
    }
}