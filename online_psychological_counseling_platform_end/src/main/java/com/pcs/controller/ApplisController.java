package com.pcs.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.pcs.common.Result;
import com.pcs.config.Upload;
import com.pcs.entity.Applis;
import com.pcs.service.ApplisService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * (Applis)表控制层
 *
 * @author makejava
 * @since 2024-04-20 21:50:53
 */
@RestController
@RequestMapping("/applis")
public class ApplisController {
    /**
     * 服务对象
     */
    @Resource
    private ApplisService applisService;

    @PostMapping("/apply")
    public Result apply(@RequestBody Applis applis) {
        return applisService.apply(applis);
    }

    /*上传头像*/
    @PostMapping("/upload")
    public Result uploadFile(@RequestParam("file") MultipartFile file) {
        return Upload.uploadFile(file);
    }

    @GetMapping("/again")
    public Result selectByPhone(String appliPhone) {
        return applisService.selectByPhone(appliPhone);
    }

    @GetMapping("/result")
    public Result selectResult(String username) {
        return applisService.selectResult(username);
    }

    @GetMapping("/all")
    public Result selectAll(String search) {
        return applisService.selectAll(search);
    }

    @PostMapping("/agreeAppli")
    public Result agreeEva(@RequestBody JsonNode jsonNode) {
        String appliPhone = jsonNode.get("appliPhone").asText();
        return applisService.agreeAppli(appliPhone);
    }

    @PostMapping("/refuseAppli")
    public Result refuseEva(@RequestBody JsonNode jsonNode) {
        String appliPhone = jsonNode.get("appliPhone").asText();
        return applisService.refuseAppli(appliPhone);
    }
}
