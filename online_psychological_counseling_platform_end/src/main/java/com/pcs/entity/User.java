package com.pcs.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName user
 */
@TableName(value = "user")
@Data
public class User implements Serializable {
    /**
     * 手机号
     */
    @TableId
    private String phoneNumber;

    /**
     * 用户名
     */
    private String username;

    /**
     * 头像地址
     */
    private String userPfp;

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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}