package com.pcs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pcs.common.Result;
import com.pcs.entity.Log;

/**
 * (Log)表服务接口
 *
 * @author makejava
 * @since 2024-06-02 14:32:44
 */
public interface LogService extends IService<Log> {
    Result all(String logLogin);

}
