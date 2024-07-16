package com.pcs.controller;


import com.fasterxml.jackson.databind.JsonNode;
import com.pcs.common.Result;
import com.pcs.config.Upload;
import com.pcs.service.AdminService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;

/**
 * (Admin)表控制层
 *
 * @author makejava
 * @since 2024-04-13 11:18:21
 */
@RestController
@RequestMapping("admin")
public class AdminController {
    /**
     * 服务对象
     */
    @Resource
    private AdminService adminService;

    @GetMapping("/selectPfp")
    public Result selectPfp(String adminName) {
        return adminService.selectPfp(adminName);
    }

    @GetMapping("/info")
    public Result selectAdmin(String adminName) {
        return adminService.selectAdmin(adminName);
    }

    /*上传头像*/
    @PostMapping("/upload")
    public Result uploadFile(@RequestParam("file") MultipartFile file) {
        return Upload.uploadFile(file);
    }

    //修改头像
    @PostMapping("/updatePfp")
    public Result updatePfp(@RequestBody JsonNode jsonNode) {
        String adminName = jsonNode.get("adminName").asText();
        String adminPfp = jsonNode.get("adminPfp").asText();
        return adminService.updatePfp(adminName, adminPfp);
    }

    @PostMapping("/updateAdmin")
    public Result updateUser(@RequestBody JsonNode jsonNode) throws ParseException {
        String adminSex = jsonNode.get("adminSex").asText();
        String adminAge = jsonNode.get("adminAge").asText();
        String adminSignature = jsonNode.get("adminSignature").asText();
        String adminName = jsonNode.get("adminName").asText();
        return adminService.updateAdmin(adminSex, adminAge, adminSignature, adminName);
    }
}

