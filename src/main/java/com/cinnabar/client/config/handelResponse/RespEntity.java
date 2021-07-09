package com.cinnabar.client.config.handelResponse;

public class RespEntity {

    private Integer code;

    private String message;

    private Object data;

    public Integer getCode() {
        return code;
    }

    void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
