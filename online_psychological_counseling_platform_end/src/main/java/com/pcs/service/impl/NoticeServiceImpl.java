package com.pcs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pcs.common.Result;
import com.pcs.entity.Notice;
import com.pcs.mapper.NoticeMapper;
import com.pcs.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * (Notice)表服务实现类
 *
 * @author makejava
 * @since 2024-06-02 16:46:10
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {
    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public Result selectAll(String search) {
        QueryWrapper<Notice> queryWrapper = new QueryWrapper<>();
        if (search != null && !search.isEmpty()) {
            queryWrapper.like("notice_time", search);
        }
        return Result.success(noticeMapper.selectList(queryWrapper));
    }


    @Override
    public Result addNotice(Notice notice) {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = formatter.format(date);
        notice.setNoticeTime(strDate);
        noticeMapper.insert(notice);
        return Result.success();
    }

    @Override
    public Result delNotice(int noticeId) {
        QueryWrapper<Notice> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("notice_id", noticeId);
        noticeMapper.delete(queryWrapper);
        return Result.success();
    }
}

