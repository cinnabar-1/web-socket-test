package com.cinnabar.client.beans;

import lombok.Data;

import java.util.Date;

/**
 * @author cinnabar-1
 * @version 1.0.0
 * @ClassName Message.java
 * @Description
 * @createTime 2021-07-09  15:43:00
 */
@Data
public class Message {
    Integer userId;
    String message;
    String dateTime;
    Integer toUserId;
}
