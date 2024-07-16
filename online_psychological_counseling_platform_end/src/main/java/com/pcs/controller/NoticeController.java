package com.pcs.controller;


import com.pcs.common.Result;
import com.pcs.entity.Notice;
import com.pcs.service.NoticeService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * (Notice)表控制层
 *
 * @author makejava
 * @since 2024-06-02 16:46:07
 */
@RestController
@RequestMapping("notice")
public class NoticeController {
    /**
     * 服务对象
     */
    @Resource
    private NoticeService noticeService;

    @GetMapping("/info")
    public Result selectAll(String search) {
        return noticeService.selectAll(search);
    }

    @PostMapping("/add")
    public Result addNotice(@RequestBody Notice notice) {
        return noticeService.addNotice(notice);
    }

    @DeleteMapping("/del")
    public Result delNotice(int noticeId) {
        return noticeService.delNotice(noticeId);
    }

}

