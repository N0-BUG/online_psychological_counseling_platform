package com.pcs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pcs.common.Result;
import com.pcs.entity.Pmessage;

/**
 * @author pcs
 * @description 针对表【pmessage】的数据库操作Service
 * @createDate 2024-04-20 14:34:56
 */
public interface PmessageService extends IService<Pmessage> {
    Result sendPM(Pmessage pmessage);

    Result selectPM(String pmSender, String search);

    /*收到的私信*/
    Result selectMy(String username, String search);

    /*回复*/
    Result reply(Integer pmId, String pmBack);

    /*查询所有*/
    Result selectAll(String search);

    /*审核通过*/
    Result agreePm(Integer pmId);

    /*审核失败*/
    Result refusePm(Integer pmId);

    /*删除私信*/
    Result delPm(int id);
}
