package com.pcs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pcs.common.Result;
import com.pcs.entity.Applis;

/**
 * (Applis)表服务接口
 *
 * @author makejava
 * @since 2024-04-20 21:51:00
 */
public interface ApplisService extends IService<Applis> {
    /*申请*/
    Result apply(Applis applis);

    /*查看是否提交过申请*/
    Result selectByPhone(String appliPhone);

    /*查看申请结果*/
    Result selectResult(String username);

    /*查询所有*/
    Result selectAll(String search);

    /*审核通过*/
    Result agreeAppli(String appliPhone);

    /*审核失败*/
    Result refuseAppli(String appliPhone);
}

