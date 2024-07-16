package com.pcs.controller;


import com.fasterxml.jackson.databind.JsonNode;
import com.pcs.common.Result;
import com.pcs.entity.Pmessage;
import com.pcs.service.PmessageService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * (Pmessage)表控制层
 *
 * @author makejava
 * @since 2024-04-13 11:13:22
 */
@RestController
@RequestMapping("/pmessage")
public class PmessageController {
    /**
     * 服务对象
     */
    @Resource
    private PmessageService pmessageService;

    @PostMapping("/send")
    public Result sendPM(@RequestBody Pmessage pmessage) {
        return pmessageService.sendPM(pmessage);
    }

    @GetMapping("/detail")
    public Result selectPM(String pmSender, @RequestParam(required = false) String search) {
        return pmessageService.selectPM(pmSender, search);
    }

    @GetMapping("/my")
    public Result selectMy(String username, @RequestParam(required = false) String search) {
        return pmessageService.selectMy(username, search);
    }

    @PostMapping("/reply")
    public Result reply(@RequestBody JsonNode jsonNode) {
        Integer pmId = jsonNode.get("pmId").asInt();
        String pmBack = jsonNode.get("pmBack").asText();
        return pmessageService.reply(pmId, pmBack);
    }

    @GetMapping("/all")
    public Result selectAll(String search) {
        return pmessageService.selectAll(search);
    }

    @PostMapping("/agreePm")
    public Result agreeEva(@RequestBody JsonNode jsonNode) {
        Integer pmId = jsonNode.get("pmId").asInt();
        return pmessageService.agreePm(pmId);
    }

    @PostMapping("/refusePm")
    public Result refuseEva(@RequestBody JsonNode jsonNode) {
        Integer pmId = jsonNode.get("pmId").asInt();
        return pmessageService.refusePm(pmId);
    }

    @DeleteMapping("/del")
    public Result delPm(int id) {
        return pmessageService.delPm(id);
    }
}

