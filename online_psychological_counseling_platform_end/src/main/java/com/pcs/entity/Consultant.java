package com.pcs.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName consultant
 */
@TableName(value = "consultant")
@Data
public class Consultant implements Serializable {
    /**
     * 手机号
     */
    @TableId
    private String consultantPhone;

    /**
     * 咨询师姓名
     */
    private String consultantName;

    /**
     * 头像地址
     */
    private String consultantPfp;

    /**
     * 咨询师擅长类型
     */
    private String consultantType;

    /**
     * 累计帮助人数
     */
    private Integer consultantNo;

    /**
     * 性别
     */
    private String consultantSex;

    /**
     * 年龄
     */
    private String consultantAge;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}