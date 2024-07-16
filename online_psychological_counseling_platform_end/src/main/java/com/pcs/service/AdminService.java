package com.pcs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pcs.common.Result;
import com.pcs.entity.Admin;

import java.text.ParseException;

/**
 * @author pcs
 * @description 针对表【admin】的数据库操作Service
 * @createDate 2024-04-11 21:51:08
 */
public interface AdminService extends IService<Admin> {
    Result selectPfp(String adminName);

    /*查找管理员信息*/
    Result selectAdmin(String adminName);

    /*更新管理员信息*/
    Result updateAdmin(String adminSex, String adminAge, String adminSignature, String adminName) throws ParseException;

    /*更新头像*/
    Result updatePfp(String adminName, String adminPfp);
}
