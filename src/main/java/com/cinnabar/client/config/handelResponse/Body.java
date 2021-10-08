package com.cinnabar.client.config.handelResponse;

/**
 * @author cinnabar-1
 * @version 1.0.0
 * @ClassName ResponseBody.java
 * @Description
 * @createTime 2020-11-18  09:54:00
 */
public interface Body<T> {
    /* 包装在controller层的简单处理逻辑，同一异常处理*/
    void include(ResponseTemplate<T> o) throws Exception;
}
