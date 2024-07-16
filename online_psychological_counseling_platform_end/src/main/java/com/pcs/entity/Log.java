package com.pcs.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * (Log)实体类
 *
 * @author makejava
 * @since 2024-06-02 14:32:28
 */
@Data
@TableName(value = "log")
public class Log implements Serializable {
    /**
     * 登录人
     */
    private String logLogin;
    /**
     * 登录时间
     */
    private String logTime;


}

