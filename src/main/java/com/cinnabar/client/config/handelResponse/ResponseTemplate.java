package com.cinnabar.client.config.handelResponse;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;


public class ResponseTemplate<T> {

    @ApiModelProperty("状态码正常200，异常202，")
    private Integer code;
    @ApiModelProperty("正常/异常信息")
    private String message;
    @ApiModelProperty("返回数据")
    private T data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
