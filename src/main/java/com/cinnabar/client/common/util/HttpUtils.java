package com.cinnabar.client.common.util;

import com.alibaba.fastjson.JSONObject;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * @author cinnabar-1
 * @version 1.0.0
 * @ClassName HttpUtils.java
 * @Description
 * @createTime 2020-11-23  10:21:00
 */
public interface HttpUtils {
    // HTTP GET请求
    void sendGet(@NotNull String url) throws Exception;

    // HTTP POST请求
    void sendPost(@NotNull String url, JSONObject jsonObject) throws Exception;

    // HTTP POST请求
    /* 需要自定义请求头，以map的形式传进来就行了 ("content-type","application/json") */
    void sendPost(@NotNull String url, JSONObject jsonObject, Map<String, String> map) throws Exception;
}
