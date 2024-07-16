package com.pcs.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName admin
 */
@TableName(value = "admin")
@Data
public class Admin implements Serializable {
    /**
     * 手机号
     */
    @TableId
    private String adminPhone;

    /**
     * 管理员名字
     */
    private String adminName;

    /**
     * 头像地址
     */
    private String adminPfp;

    /**
     *
     */
    private String adminSex;

    /**
     *
     */
    private String adminAge;

    /**
     *
     */
    private String adminSignature;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}