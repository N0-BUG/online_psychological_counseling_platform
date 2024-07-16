package com.pcs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pcs.common.Result;
import com.pcs.entity.Account;
import com.pcs.entity.Log;
import com.pcs.entity.User;
import com.pcs.mapper.AccountMapper;
import com.pcs.mapper.AdminMapper;
import com.pcs.mapper.LogMapper;
import com.pcs.mapper.UserMapper;
import com.pcs.service.AccountService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author pcs
 * @description 针对表【account】的数据库操作Service实现
 * @createDate 2024-04-11 21:51:08
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account>
        implements AccountService {
    @Resource
    private AccountMapper accountMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private LogMapper logMapper;

    public Result login(String input, String password, String accountIdentify) {
        Account account;
        Log log = new Log();
        // 判断input是否是手机号格式
        if (isValidPhoneNumber(input)) {
            // 是手机号,通过手机号查询
            account = accountMapper.selectOne(new QueryWrapper<Account>()
                    .eq("phone_number", input));
        } else {
            // 不是手机号,通过用户名查询
            account = accountMapper.selectOne(new QueryWrapper<Account>()
                    .eq("username", input));
        }
        if (account == null) {
            return Result.fail("用户不存在请注册");
        }
        // 再进行密码和身份比对
        if (!password.equals(account.getPassword())) {
            return Result.fail("账号或密码错误请重新输入");
        }
        if (!accountIdentify.equals(account.getAccountIdentify())) {
            return Result.fail("账号身份不匹配请确认");
        }
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = formatter.format(date);
        log.setLogLogin(account.getUsername());
        log.setLogTime(strDate);
        logMapper.insert(log);
        return Result.success(account);
    }

    private static boolean isValidPhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile("^1[3-9]\\d{9}$");
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    public Result register(Account account) {

        Account isAccount1 = accountMapper.selectOne(new QueryWrapper<Account>()
                .eq("phone_number", account.getPhoneNumber()));
        Account isAccount2 = accountMapper.selectOne(new QueryWrapper<Account>()
                .eq("username", account.getUsername()));
        //判断手机号是否被占用
        if (isAccount1 != null) {
            return Result.fail("该手机号已注册");
        }
        //判断用户名是否被占用
        if (isAccount2 != null) {
            return Result.fail("该用户名已被使用");
        }
        accountMapper.insert(account);
        User user = new User();
        user.setUsername(account.getUsername());
        user.setPhoneNumber(account.getPhoneNumber());
        userMapper.insert(user);

        return Result.success("注册成功！");
    }

    @Override
    public Result isConsultant(String username) {
        Account account = accountMapper.selectOne(new QueryWrapper<Account>()
                .select("is_consultant").eq("username", username));
        return Result.success(account.getIsConsultant());
    }

    @Override
    public Result checkPassword(String username) {

        return Result.success(accountMapper.selectOne(new QueryWrapper<Account>().eq("username", username)));
    }

    @Override
    public Result updatePs(String username, String password) {
        UpdateWrapper<Account> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("username", username);
        updateWrapper.set("password", password);
        accountMapper.update(null, updateWrapper);
        return Result.success();
    }

    @Override
    public Result updateUs(String username, String newUsername) {
        Account account = accountMapper.selectOne(new QueryWrapper<Account>()
                .eq("username", newUsername));
        if (account != null) {
            return Result.fail("该用户名已被使用");
        }
        //更新账号表用户名
        Account account1 = accountMapper.selectOne(new QueryWrapper<Account>()
                .eq("username", username));
        String phoneNumber = account1.getPhoneNumber();
        UpdateWrapper<Account> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("phone_number", phoneNumber);
        updateWrapper.set("username", newUsername);
        accountMapper.update(null, updateWrapper);

        return Result.success();

    }

    @Override
    public Result remove(String username) {
        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        accountMapper.delete(queryWrapper);
        return Result.success();
    }
}




