package com.pcs.entity;

import lombok.Data;

@Data
public class UpdateUserDto {
    /**
     * 用户名
     */
    private String username;


    /**
     * 个性签名
     */
    private String userSignature;

    /**
     * 年龄
     */
    private String userAge;

    /**
     * 性别
     */
    private String userSex;

}
