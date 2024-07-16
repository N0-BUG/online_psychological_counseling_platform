package com.pcs.config;

import com.pcs.common.Result;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class Upload {
    public static Result uploadFile(MultipartFile file) {
        try {
            // 保存文件到指定路径，这里假设保存在当前目录下的 uploads 文件夹中
            String filePath = "D:\\WebStorm项目\\online_psychological_counseling_platform\\src\\assets\\" + file.getOriginalFilename();
            file.transferTo(new File(filePath));
            String relativePath = "/src/assets/" + file.getOriginalFilename();
            return Result.success(relativePath);
        } catch (IOException e) {
            return Result.fail("Failed to upload file.");
        }

    }
}
