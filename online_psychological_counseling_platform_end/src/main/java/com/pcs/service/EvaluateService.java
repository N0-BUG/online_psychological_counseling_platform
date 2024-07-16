package com.pcs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pcs.common.Result;
import com.pcs.entity.Evaluate;

/**
 * @author pcs
 * @description 针对表【evaluate】的数据库操作Service
 * @createDate 2024-04-11 21:51:08
 */
public interface EvaluateService extends IService<Evaluate> {
    /*通过被评价者查询*/
    Result selectByName(String evaluated);

    /*通过评价者查询*/
    Result selectByUser(String evaluate, String search);

    /*删除评价*/
    Result delEva(int id);

    /*通过用户名查询*/
    Result selectEva(String username, String search);

    /*查询所有*/
    Result selectAll(String search);

    /*审核通过*/
    Result agreeEva(Integer evaluatedId);

    /*审核失败*/
    Result refuseEva(Integer evaluatedId);

    Result evaluate(Evaluate evaluate);
//
//    Result againEva(String evaluate, String evaluated);
}
