package com.pcs.controller;

import com.pcs.common.Result;
import com.pcs.service.impl.LogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/log")
public class LogController {
    @Autowired
    private LogServiceImpl logService;

    @GetMapping("/all")
    public Result all(String logLogin) {
        return logService.all(logLogin);
    }
}
