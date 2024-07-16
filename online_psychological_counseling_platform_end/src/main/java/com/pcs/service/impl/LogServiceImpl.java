package com.pcs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pcs.common.Result;
import com.pcs.entity.Log;
import com.pcs.mapper.LogMapper;
import com.pcs.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * (Log)表服务实现类
 *
 * @author makejava
 * @since 2024-06-02 14:32:44
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log>
        implements LogService {
    @Autowired
    private LogMapper logMapper;

    @Override
    public Result all(String logLogin) {
        return Result.success(logMapper.selectList(new
                QueryWrapper<Log>().eq("log_login", logLogin)));

    }
}
