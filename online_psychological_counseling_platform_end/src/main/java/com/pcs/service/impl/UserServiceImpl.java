package com.pcs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pcs.common.Result;
import com.pcs.config.ComputedAge;
import com.pcs.entity.Account;
import com.pcs.entity.Consultant;
import com.pcs.entity.User;
import com.pcs.mapper.AccountMapper;
import com.pcs.mapper.ConsultantMapper;
import com.pcs.mapper.UserMapper;
import com.pcs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;

/**
 * @author pcs
 * @description 针对表【user】的数据库操作Service实现
 * @createDate 2024-04-11 21:51:08
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private ConsultantMapper consultantMapper;

    public Result selectPfp(String username) {

        User user = userMapper.selectOne(new QueryWrapper<User>().eq("username", username));
        return Result.success(user.getUserPfp());
    }

    @Override
    public Result selectUser(String username) {
        return Result.success(userMapper.selectOne
                (new QueryWrapper<User>().eq("username", username)));
    }

    @Override
    public Result updateUser(String userSex, String userAge, String userSignature, String username) throws ParseException {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("username", username);
        if (userSex != null) {
            updateWrapper.set("user_sex", userSex);
        }
        if (userAge != null) {
            int age = ComputedAge.getAge(userAge);
            updateWrapper.set("user_age", age);
        }
        if (userSignature != null) {
            updateWrapper.set("user_signature", userSignature);
        }
        userMapper.update(null, updateWrapper);

        return Result.success();
    }
    //更新头像

    @Override
    public Result updatePfp(String username, String userPfp) {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("username", username);
        updateWrapper.set("user_pfp", userPfp);
        userMapper.update(null, updateWrapper);
        return Result.success();
    }

    @Override
    public Result selectAll(String search) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (search != null && !search.isEmpty()) {
            queryWrapper.like("username", search);
        }
        return Result.success(userMapper.selectList(queryWrapper));
    }

    @Override
    public Result addUser(String username, String userSex, String userAge, String phoneNumber) {
        Account account = new Account();
        account.setUsername(username);
        account.setPhoneNumber(phoneNumber);
        account.setAccountIdentify("user");
        accountMapper.insert(account);
        User user = new User();
        user.setUsername(username);
        user.setUserSex(userSex);
        user.setUserAge(userAge);
        user.setPhoneNumber(phoneNumber);
        userMapper.insert(user);
        return Result.success();
    }

    @Override
    public Result selectChatPfp(String username) {
//        ChatPfp chatPfp = new ChatPfp();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        QueryWrapper<Consultant> queryWrapper1 = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User user = userMapper.selectOne(queryWrapper);
        if (user != null) {
//            queryWrapper1.eq("consultant_name", chatUser);
//            Consultant consultant = consultantMapper.selectOne(queryWrapper1);
//            chatPfp.setUserPfp(user.getUserPfp());
//            chatPfp.setChatUserPfp(consultant.getConsultantPfp());
            return Result.success(user.getUserPfp());
        }
//        QueryWrapper<User> queryWrapper2 = new QueryWrapper<>();
//        queryWrapper2.eq("username", chatUser);
//        User chat = userMapper.selectOne(queryWrapper2);
        queryWrapper1.eq("consultant_name", username);
        Consultant consultant = consultantMapper.selectOne(queryWrapper1);
//        chatPfp.setUserPfp(consultant.getConsultantPfp());
//        chatPfp.setChatUserPfp(chat.getUserPfp());
        return Result.success(consultant.getConsultantPfp());
    }
}



