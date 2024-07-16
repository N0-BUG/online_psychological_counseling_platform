package com.pcs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pcs.common.Result;
import com.pcs.entity.User;

import java.text.ParseException;

/**
 * @author pcs
 * @description 针对表【user】的数据库操作Service
 * @createDate 2024-04-11 21:51:08
 */
public interface UserService extends IService<User> {
    //通过用户名查询头像
    Result selectPfp(String username);

    //通过用户名查找用户信息
    Result selectUser(String username);

    Result updateUser(String userSex, String userAge, String userSignature, String username) throws ParseException;

    Result updatePfp(String username, String userPfp);

    Result selectAll(String search);

    Result addUser(String username, String userSex, String userAge, String phoneNumber);

    /*查询聊天室头像*/
    Result selectChatPfp(String username);

}
