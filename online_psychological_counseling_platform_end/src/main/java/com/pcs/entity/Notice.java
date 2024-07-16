package com.pcs.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * (Notice)表实体类
 *
 * @author makejava
 * @since 2024-06-02 16:46:08
 */
@Data
public class Notice implements Serializable {
    private int noticeId;
    //公告标题
    private String noticeTitle;
    //公告信息
    private String noticeInfo;
    //发布时间
    private String noticeTime;
    //发布人
    private String noticePublisher;


}

