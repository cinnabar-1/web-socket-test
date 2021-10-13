package com.cinnabar.client.service;

import com.cinnabar.client.beans.Message;
import com.cinnabar.client.beans.User;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * @author cinnabar-1
 * @version 1.0.0
 * @ClassName MessageService.java
 * @Description
 * @createTime 2021-07-09  15:37:00
 */
public interface MessageService {
    Integer saveDelayMessage(List<Message> messages);

    List<Message> getDelayMessage(Integer toUserId);

    void deleteDelayMessage(Integer toUserId);

    User getUserByAccount(String account);
}
