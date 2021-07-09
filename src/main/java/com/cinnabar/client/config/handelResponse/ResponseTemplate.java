package com.cinnabar.client.config.handelResponse;

import lombok.Builder;


@Builder
public class ResponseTemplate {

    private Integer code;

    private String message;

    private Object data;
}
