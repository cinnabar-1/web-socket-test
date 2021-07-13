package com.cinnabar.client.mapper;

import com.cinnabar.client.beans.Message;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author cinnabar-1
 * @version 1.0.0
 * @ClassName MessageMapper.java
 * @Description
 * @createTime 2021-07-09  15:40:00
 */
@Mapper
@Component(value = "MessageMapper")
public interface MessageMapper {
    Integer saveMessage(List<Message> list);

    List<Message> getDelayMessage(String toUserId);

    void deleteDelayMessage(String toUserId);
}
