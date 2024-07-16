package com.pcs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pcs.common.Result;
import com.pcs.entity.Account;

/**
 * @author pcs
 * @description 针对表【account】的数据库操作Service
 * @createDate 2024-04-11 21:51:08
 */
public interface AccountService extends IService<Account> {
    Result login(String input, String password, String accountIdentify);

    Result register(Account account);

    //判断用户身份
    Result isConsultant(String username);

    //判断密码
    Result checkPassword(String username);

    //修改密码
    Result updatePs(String username, String password);

    Result updateUs(String username, String newUsername);

    /*注销账号*/
    Result remove(String username);
}
