package com.pcs.controller;


import com.fasterxml.jackson.databind.JsonNode;
import com.pcs.common.Result;
import com.pcs.config.Upload;
import com.pcs.service.ConsultantService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;

/**
 * (Consultant)表控制层
 *
 * @author makejava
 * @since 2024-04-13 11:18:21
 */
@RestController
@RequestMapping("/consultant")
public class ConsultantController {
    /**
     * 服务对象
     */
    @Resource
    private ConsultantService consultantService;

    @GetMapping("/findAll")
    public Result selectAll(@RequestParam Integer pageNum, @RequestParam Integer pageSize,
                            @RequestParam(required = false) String input) {
        return consultantService.selectAll(pageNum, pageSize, input);
    }

    @GetMapping("/detail")
    public Result selectByPhone(String consultantPhone) {

        return consultantService.selectByPhone(consultantPhone);
    }

    @GetMapping("/info")
    public Result selectByName(String username) {

        return consultantService.selectByName(username);
    }

    @PostMapping("/updateConsultant")
    public Result updateConsultant(@RequestBody JsonNode jsonNode) throws ParseException {
        String consultantSex = jsonNode.get("consultantSex").asText();
        String consultantAge = jsonNode.get("consultantAge").asText();
        String consultantType = jsonNode.get("consultantType").asText();
        String username = jsonNode.get("username").asText();
        return consultantService.updateConsultant(consultantSex, consultantAge, consultantType, username);
    }

    /*上传头像*/
    @PostMapping("/upload")
    public Result uploadFile(@RequestParam("file") MultipartFile file) {
        return Upload.uploadFile(file);
    }

    /*更新头像*/
    @PostMapping("/updatePfp")
    public Result updatePfp(@RequestBody JsonNode jsonNode) {
        String username = jsonNode.get("username").asText();
        String consultantPfp = jsonNode.get("consultantPfp").asText();
        return consultantService.updatePfp(username, consultantPfp);
    }

    @GetMapping("/all")
    public Result selectAll(String search) {
        return consultantService.selectAll(search);
    }

    @DeleteMapping("/del")
    public Result del(String consultantName) {
        return consultantService.del(consultantName);
    }

    @PostMapping("/add")
    public Result add(@RequestBody JsonNode jsonNode) {
        String consultantSex = jsonNode.get("consultantSex").asText();
        String consultantAge = jsonNode.get("consultantAge").asText();
        String consultantType = jsonNode.get("consultantType").asText();
        String consultantName = jsonNode.get("consultantName").asText();
        String consultantPhone = jsonNode.get("consultantPhone").asText();
        return consultantService.add(consultantSex, consultantAge,
                consultantType, consultantName, consultantPhone);
    }

    @GetMapping("/selectPfp")
    public Result selectPfp(String username) {
        return consultantService.selectPfp(username);
    }
}

