package com.pcs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pcs.common.Result;
import com.pcs.entity.Notice;

/**
 * (Notice)表服务接口
 *
 * @author makejava
 * @since 2024-06-02 16:46:09
 */
public interface NoticeService extends IService<Notice> {
    //查找公告
    Result selectAll(String search);

    //发布公告
    Result addNotice(Notice notice);

    //删除公告
    Result delNotice(int noticeId);
}

