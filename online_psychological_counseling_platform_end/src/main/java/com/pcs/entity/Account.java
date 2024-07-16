package com.pcs.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName account
 */
@TableName(value = "account")
@Data
public class Account implements Serializable {
    /**
     * 手机号
     */

    private String phoneNumber;

    /**
     * 用户名
     */
    @TableId
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户身份
     */
    private String accountIdentify;
    /*是否成为咨询师
     * */
    private String isConsultant;
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}