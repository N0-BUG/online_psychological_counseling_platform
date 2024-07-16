package com.pcs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pcs.common.Result;
import com.pcs.config.ComputedAge;
import com.pcs.entity.Account;
import com.pcs.entity.Applis;
import com.pcs.entity.Consultant;
import com.pcs.mapper.AccountMapper;
import com.pcs.mapper.ApplisMapper;
import com.pcs.mapper.ConsultantMapper;
import com.pcs.service.ConsultantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;

/**
 * @author pcs
 * @description 针对表【consultant】的数据库操作Service实现
 * @createDate 2024-04-11 21:51:08
 */
@Service
public class ConsultantServiceImpl extends ServiceImpl<ConsultantMapper, Consultant>
        implements ConsultantService {
    @Autowired
    private ConsultantMapper consultantMapper;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private ApplisMapper applisMapper;

    public Result selectAll(Integer pageNum, Integer pageSize, String input) {

        String keyword = input;

        Page<Consultant> page = new Page<>(pageNum, pageSize);

        QueryWrapper<Consultant> queryWrapper = new QueryWrapper<>();

        if (keyword != null) {
            queryWrapper.like("consultant_name", keyword);
        }

        Page<Consultant> consultantPage = consultantMapper.selectPage(page, queryWrapper);

        return Result.success(consultantPage);
    }

    public Result selectByPhone(String consultantPhone) {
        return Result.success(consultantMapper.selectOne(
                new QueryWrapper<Consultant>().eq("consultant_phone", consultantPhone)));
    }

    public Result selectByName(String username) {
        Account account = accountMapper.selectOne(new
                QueryWrapper<Account>().eq("username", username));
        return Result.success(consultantMapper.selectOne(
                new QueryWrapper<Consultant>().eq("consultant_phone", account.getPhoneNumber())));
    }

    @Override
    public Result updateConsultant(String consultantSex, String consultantAge,
                                   String consultantType, String username) throws ParseException {
        UpdateWrapper<Consultant> updateWrapper = new UpdateWrapper<>();
        Account account = accountMapper.selectOne(new
                QueryWrapper<Account>().eq("username", username));
        updateWrapper.eq("consultant_phone", account.getPhoneNumber());
        if (!consultantSex.equals("null")) {
            updateWrapper.set("consultant_sex", consultantSex);
        }

        if (!consultantAge.equals("null")) {
            int age = ComputedAge.getAge(consultantAge);
            updateWrapper.set("consultant_age", age);
        }
        if (!consultantType.equals("null")) {
            updateWrapper.set("consultant_type", consultantType);
        }
        consultantMapper.update(null, updateWrapper);

        return Result.success();
    }

    @Override
    public Result updatePfp(String username, String consultantPfp) {
        Account account = accountMapper.selectOne(new
                QueryWrapper<Account>().eq("username", username));
        UpdateWrapper<Consultant> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("consultant_phone", account.getPhoneNumber());
        updateWrapper.set("consultant_pfp", consultantPfp);
        consultantMapper.update(null, updateWrapper);
        return Result.success();
    }

    @Override
    public Result selectAll(String search) {
        QueryWrapper<Consultant> queryWrapper = new QueryWrapper<>();
        if (search != null && !search.isEmpty()) {
            queryWrapper.like("consultant_name", search);
        }
        return Result.success(consultantMapper.selectList(queryWrapper));
    }

    public Result del(String consultantName) {
        Consultant consultant = consultantMapper.selectOne(new
                QueryWrapper<Consultant>().eq("consultant_name", consultantName));
        accountMapper.delete(new
                QueryWrapper<Account>().eq("phone_number", consultant.getConsultantPhone()));
        QueryWrapper<Consultant> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("consultant_name", consultantName);
        consultantMapper.delete(queryWrapper);
        return Result.success();
    }

    public Result add(String consultantSex, String consultantAge,
                      String consultantType, String consultantName, String consultantPhone) {
        Applis applis = new Applis();
        applis.setAppliName(consultantName);
        applis.setAppliPhone(consultantPhone);
        applis.setAppliType(consultantType);
        applis.setAppliStatus("申请成功");
        applisMapper.insert(applis);
        Account account = new Account();
        account.setUsername(consultantName);
        account.setPhoneNumber(consultantPhone);
        account.setAccountIdentify("user");
        account.setIsConsultant("yes");
        accountMapper.insert(account);
        Consultant consultant = new Consultant();
        consultant.setConsultantSex(consultantSex);
        consultant.setConsultantName(consultantName);
        consultant.setConsultantAge(consultantAge);
        consultant.setConsultantType(consultantType);
        consultant.setConsultantPhone(consultantPhone);
        consultantMapper.insert(consultant);
        return Result.success();
    }

    @Override
    public Result selectPfp(String username) {
        Account account = accountMapper.selectOne(new
                QueryWrapper<Account>().eq("username", username));
        Consultant consultant = consultantMapper.selectOne(new
                QueryWrapper<Consultant>().eq("consultant_phone", account.getPhoneNumber()));
        return Result.success(consultant.getConsultantPfp());
    }
}




