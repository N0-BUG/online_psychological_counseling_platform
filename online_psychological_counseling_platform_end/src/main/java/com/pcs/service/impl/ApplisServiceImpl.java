package com.pcs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pcs.common.Result;
import com.pcs.entity.Account;
import com.pcs.entity.Applis;
import com.pcs.entity.Consultant;
import com.pcs.entity.User;
import com.pcs.mapper.AccountMapper;
import com.pcs.mapper.ApplisMapper;
import com.pcs.mapper.ConsultantMapper;
import com.pcs.mapper.UserMapper;
import com.pcs.service.ApplisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * (Applis)表服务实现类
 *
 * @author makejava
 * @since 2024-04-20 21:51:00
 */
@Service
public class ApplisServiceImpl extends ServiceImpl<ApplisMapper, Applis> implements ApplisService {
    @Autowired
    private ApplisMapper applisMapper;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private ConsultantMapper consultantMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public Result apply(Applis applis) {
        // 获取当前日期
        LocalDate currentDate = LocalDate.now();
        applis.setAppliTime(currentDate);
        applisMapper.insert(applis);
        return Result.success();
    }

    @Override
    public Result selectByPhone(String appliPhone) {
        Applis applis = applisMapper.selectOne(
                new QueryWrapper<Applis>().eq("appli_phone", appliPhone));
        if (applis == null) {
            return Result.fail();
        }
        return Result.success();
    }

    @Override
    public Result selectResult(String username) {
        Account account = accountMapper.selectOne(new QueryWrapper<Account>().eq("username", username));
        Applis applis = applisMapper.selectOne(
                new QueryWrapper<Applis>().eq("appli_phone", account.getPhoneNumber()));
        if (applis == null) {
            return Result.success("未提交");
        }
        return Result.success(applis.getAppliStatus());
    }

    @Override
    public Result selectAll(String search) {
        QueryWrapper<Applis> queryWrapper = new QueryWrapper<>();
        if (search != null && !search.isEmpty()) {
            queryWrapper.eq("appli_status", "处理中")
                    .like("appli_name", search);
        }
        queryWrapper.eq("appli_status", "处理中");
        return Result.success(applisMapper.selectList(queryWrapper));
    }

    @Override
    public Result agreeAppli(String appliPhone) {
        Applis applis = applisMapper.selectOne(new
                QueryWrapper<Applis>().eq("appli_phone", appliPhone));
        //更新用户身份
        UpdateWrapper<Account> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("phone_number", appliPhone).
                set("is_consultant", "yes");
        accountMapper.update(null, updateWrapper);
        //更新申请表状态
        UpdateWrapper<Applis> updateWrapper1 = new UpdateWrapper<>();
        updateWrapper1.eq("appli_phone", appliPhone).set("appli_status", "申请成功");
        applisMapper.update(null, updateWrapper1);
        //删除用户表的信息
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone_number", appliPhone);
        userMapper.delete(queryWrapper);
        //将申请表人信息填入咨询师表
        Consultant consultant = new Consultant();
        consultant.setConsultantName(applis.getAppliName());
        consultant.setConsultantType(applis.getAppliType());
        consultant.setConsultantPhone(appliPhone);
        consultant.setConsultantPfp(applis.getAppliPfp());
        consultantMapper.insert(consultant);
        return Result.success();
    }

    @Override
    public Result refuseAppli(String appliPhone) {
        UpdateWrapper<Applis> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("appli_phone", appliPhone).set("appli_status", "申请失败");
        applisMapper.update(null, updateWrapper);
        return Result.success();
    }
}

