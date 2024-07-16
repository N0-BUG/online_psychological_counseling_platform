package com.pcs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pcs.common.Result;
import com.pcs.entity.Consultant;

import java.text.ParseException;

/**
 * @author pcs
 * @description 针对表【consultant】的数据库操作Service
 * @createDate 2024-04-11 21:51:08
 */
public interface ConsultantService extends IService<Consultant> {

    Result selectAll(Integer pageNum, Integer pageSize, String input);

    /*手机号查询*/
    Result selectByPhone(String consultantPhone);

    /*用户名查询*/
    Result selectByName(String username);

    /*修改信息*/
    Result updateConsultant(String consultantSex, String consultantAge,
                            String consultantType, String username) throws ParseException;

    /*修改头像*/
    Result updatePfp(String username, String consultantPfp);

    Result selectAll(String search);

    Result del(String consultantName);

    /*添加咨询师*/
    Result add(String consultantSex, String consultantAge,
               String consultantType, String consultantName, String consultantPhone);

    /*查找头像*/
    Result selectPfp(String username);

}
