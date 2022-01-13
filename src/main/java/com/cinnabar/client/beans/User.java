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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", nick_name='" + nick_name + '\'' +
                ", birthday='" + birthday + '\'' +
                ", email='" + email + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}

