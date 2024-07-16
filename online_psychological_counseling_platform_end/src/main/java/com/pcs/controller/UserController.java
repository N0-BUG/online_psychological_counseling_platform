package com.pcs.controller;


import com.fasterxml.jackson.databind.JsonNode;
import com.pcs.common.Result;
import com.pcs.config.Upload;
import com.pcs.entity.UpdateUserDto;
import com.pcs.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;


/**
 * (User)表控制层
 *
 * @author makejava
 * @since 2024-04-13 11:13:22
 */
@RestController
@RequestMapping("/user")
public class UserController {
    /**
     * 服务对象
     */
    @Resource
    private UserService userService;

    //查找头像
    @GetMapping("/selectPfp")
    public Result selectPfp(String username) {
        return userService.selectPfp(username);
    }

    //查找用户信息
    @GetMapping("/detail")
    public Result selectUser(String username) {
        return userService.selectUser(username);
    }

    /*上传头像*/
    @PostMapping("/upload")
    public Result uploadFile(@RequestParam("file") MultipartFile file) {
        return Upload.uploadFile(file);
    }

    //修改头像
    @PostMapping("/updatePfp")
    public Result updatePfp(@RequestBody JsonNode jsonNode) {
        String username = jsonNode.get("username").asText();
        String userPfp = jsonNode.get("userPfp").asText();
        return userService.updatePfp(username, userPfp);
    }

    @PostMapping("/updateUser")
    public Result updateUser(@RequestBody UpdateUserDto updateUserDto) throws ParseException {
        String userSex = updateUserDto.getUserSex();
        String userAge = updateUserDto.getUserAge();
        String userSignature = updateUserDto.getUserSignature();
        String username = updateUserDto.getUsername();
        return userService.updateUser(userSex, userAge, userSignature, username);
    }

    @GetMapping("/info")
    public Result selectAll(@RequestParam(required = false) String search) {
        return userService.selectAll(search);
    }

    @PostMapping("/add")
    public Result addUser(@RequestBody JsonNode jsonNode) {
        String username = jsonNode.get("username").asText();
        String userSex = jsonNode.get("userSex").asText();
        String userAge = jsonNode.get("userAge").asText();
        String phoneNumber = jsonNode.get("phoneNumber").asText();
        return userService.addUser(username, userSex, userAge, phoneNumber);
    }

    @GetMapping("/chatPfp")
    public Result selectChatPfp(String username) {
        return userService.selectChatPfp(username);
    }
}

