package com.pcs.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName reservation
 */
@TableName(value = "reservation")
@Data
public class Reservation implements Serializable {
    /**
     * 预约人
     */
    @TableId
    private String reservationUser;

    /**
     * 预约时间
     */
    private String reservationTime;

    /**
     * 提交预约时间
     */
    private String reservationNow;

    /**
     * 预约的咨询师
     */
    private String reservationConsultant;

    /**
     * 相关备注
     */
    private String reservationMessage;

    /**
     * 预约状态
     */
    private String reservationStatus;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}