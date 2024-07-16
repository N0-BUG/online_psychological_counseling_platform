package com.pcs.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.pcs.common.Result;
import com.pcs.entity.Evaluate;
import com.pcs.service.EvaluateService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;


/**
 * (Evaluate)表控制层
 *
 * @author makejava
 * @since 2024-04-13 11:13:22
 */
@RestController
@RequestMapping("/evaluate")
public class EvaluateController {
    /**
     * 服务对象
     */
    @Resource
    private EvaluateService evaluateService;

    @GetMapping("/consultant")
    public Result selectByName(String evaluated) {
        return evaluateService.selectByName(evaluated);
    }

    @GetMapping("/user")
    public Result selectByUser(String evaluate, @RequestParam(required = false) String search) {
        return evaluateService.selectByUser(evaluate, search);
    }

    @DeleteMapping("/del")
    public Result delEva(int id) {
        return evaluateService.delEva(id);
    }

    @GetMapping("/username")
    public Result selectEva(String username, @RequestParam(required = false) String search) {
        return evaluateService.selectEva(username, search);
    }

    @GetMapping("/all")
    public Result selectAll(String search) {
        return evaluateService.selectAll(search);
    }

    @PostMapping("/agreeEva")
    public Result agreeEva(@RequestBody JsonNode jsonNode) {
        Integer evaluateId = jsonNode.get("evaluateId").asInt();
        return evaluateService.agreeEva(evaluateId);
    }

    @PostMapping("/refuseEva")
    public Result refuseEva(@RequestBody JsonNode jsonNode) {
        Integer evaluateId = jsonNode.get("evaluateId").asInt();
        return evaluateService.refuseEva(evaluateId);
    }

    @PostMapping("/eva")
    public Result evaluate(@RequestBody Evaluate evaluate) {
        return evaluateService.evaluate(evaluate);
    }

//    @GetMapping("/againEva")
//    public Result againEva(String evaluate, String evaluated) {
//        return evaluateService.againEva(evaluate, evaluated);
//    }
}

