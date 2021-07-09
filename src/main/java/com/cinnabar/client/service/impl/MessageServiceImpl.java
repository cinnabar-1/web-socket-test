package com.cinnabar.client.service.impl;

import com.cinnabar.client.beans.Message;
import com.cinnabar.client.mapper.MessageMapper;
import com.cinnabar.client.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author cinnabar-1
 * @version 1.0.0
 * @ClassName MessageServiceImpl.java
 * @Description
 * @createTime 2021-07-09  15:37:00
 */
@Service
@Transactional
public class MessageServiceImpl implements MessageService {
    @Autowired
    MessageMapper messageMapper;

    @Override
    public Integer saveDelayMessage(List<Message> messages) {
        return messageMapper.saveMessage(messages);
    }
}