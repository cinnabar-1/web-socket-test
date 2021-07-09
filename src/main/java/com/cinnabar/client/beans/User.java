package com.cinnabar.client.beans;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    private Integer id;

    @ApiModelProperty(notes = "用户名")
    private String account;

    private String password;

    private String surname;

    private String name;

    private String nickName;

    private String gender;

    private Integer age;

    private String nick_name;

    private String birthday;

    private String email;

    private String comment;
}

