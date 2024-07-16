package com.pcs.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @TableName evaluate
 */
@TableName(value = "evaluate")
@Data
public class Evaluate implements Serializable {
    /**
     * 评价id
     */
    @TableId(type = IdType.AUTO)
    private Integer evaluateId;

    /**
     * 评价者
     */
    private String evaluate;

    /**
     * 被评价者
     */
    private String evaluated;

    /**
     * 评价时间
     */
    private LocalDate evaluateTime;

    /**
     * 评价信息
     */
    private String evaluateMessage;

    /**
     * 是否匿名yes匿名，no不匿名
     */
    private String evaluateShow;

    /**
     * 审核情况
     */
    private String evaluateExa;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}