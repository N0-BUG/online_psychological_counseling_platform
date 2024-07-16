package com.pcs.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @TableName pmessage
 */
@TableName(value = "pmessage")
@Data
public class Pmessage implements Serializable {

    @TableId
    private Integer pmId;
    /**
     * 私信发送者
     */
    private String pmSender;

    /**
     * 私信接收者
     */

    private String pmRecipient;

    /**
     * 发送私信时间
     */

    private LocalDate pmTime;

    /**
     * 私信内容
     */
    private String pmContent;

    /**
     * 回复内容
     */
    private String pmBack;

    /**
     * 审核情况
     */
    private String pmExa;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}