package com.pcs.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * (Applis)表实体类
 *
 * @author makejava
 * @since 2024-04-20 21:51:00
 */
@SuppressWarnings("serial")
@Data
public class Applis implements Serializable {
    //申请人姓名
    private String appliName;
    //申请时间
    private LocalDate appliTime;
    //擅长类型
    private String appliType;
    //申请状态
    private String appliStatus;
    //申请人手机号
    @TableId
    private String appliPhone;
    //头像地址
    private String appliPfp;
}
