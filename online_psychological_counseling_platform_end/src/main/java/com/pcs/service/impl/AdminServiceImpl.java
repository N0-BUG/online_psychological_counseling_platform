package com.pcs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pcs.common.Result;
import com.pcs.config.ComputedAge;
import com.pcs.entity.Admin;
import com.pcs.mapper.AdminMapper;
import com.pcs.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;

/**
 * @author pcs
 * @description 针对表【admin】的数据库操作Service实现
 * @createDate 2024-04-11 21:51:08
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin>
        implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    public Result selectPfp(String adminName) {
        Admin admin = adminMapper.selectOne(new QueryWrapper<Admin>().eq("admin_name", adminName));
        return Result.success(admin.getAdminPfp());
    }

    @Override
    public Result selectAdmin(String adminName) {
        return Result.success(adminMapper.selectOne
                (new QueryWrapper<Admin>().eq("admin_name", adminName)));
    }

    @Override
    public Result updateAdmin(String adminSex, String adminAge, String adminSignature, String adminName) throws ParseException {
        UpdateWrapper<Admin> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("admin_name", adminName);
        if (!adminSex.equals("null")) {
            updateWrapper.set("admin_sex", adminSex);
        }
        if (!adminAge.equals("null")) {
            int age = ComputedAge.getAge(adminAge);
            updateWrapper.set("admin_age", age);
        }
        if (!adminSignature.equals("null")) {
            updateWrapper.set("admin_signature", adminSignature);
        }
        adminMapper.update(null, updateWrapper);

        return Result.success();
    }
    //更新头像

    @Override
    public Result updatePfp(String adminName, String adminPfp) {
        UpdateWrapper<Admin> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("admin_name", adminName);
        updateWrapper.set("admin_pfp", adminPfp);
        adminMapper.update(null, updateWrapper);
        return Result.success();
    }
}




