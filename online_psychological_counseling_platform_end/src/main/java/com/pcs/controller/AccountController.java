package com.pcs.controller;


import com.fasterxml.jackson.databind.JsonNode;
import com.pcs.common.Result;
import com.pcs.entity.Account;
import com.pcs.entity.LoginRequest;
import com.pcs.service.AccountService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * (Account)表控制层
 *
 * @author makejava
 * @since 2024-04-13 11:18:20
 */
@RestController
@RequestMapping("/account")
public class AccountController {
    /**
     * 服务对象
     */
    @Resource
    private AccountService accountService;

    @PostMapping("/login")

    public Result login(@RequestBody LoginRequest loginRequest) {

        // 获取请求体中的username, password, accountIdentify
        String input = loginRequest.getInput();
        String password = loginRequest.getPassword();
        String accountIdentify = loginRequest.getAccountIdentify();
        return accountService.login(input, password, accountIdentify);
    }

    @PostMapping("/register")
    public Result register(@RequestBody Account account) {
        return accountService.register(account);
    }

    @GetMapping("/isConsultant")
    public Result isConsultant(String username) {
        return accountService.isConsultant(username);
    }

    //检查密码
    @GetMapping("/check")
    public Result checkPassword(String username) {
        return accountService.checkPassword(username);
    }

    //修改密码
    @PostMapping("/updatePs")
    public Result updatePs(@RequestBody JsonNode jsonNode) {
        String username = jsonNode.get("username").asText();
        String password = jsonNode.get("password").asText();
        return accountService.updatePs(username, password);
    }

    @PostMapping("/updateUs")
    public Result updateUs(@RequestBody JsonNode jsonNode) {
        String username = jsonNode.get("username").asText();
        String newUsername = jsonNode.get("newUsername").asText();
        return accountService.updateUs(username, newUsername);
    }

    @DeleteMapping("/remove")
    public Result remove(String username) {
        return accountService.remove(username);
    }
}

