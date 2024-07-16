package com.pcs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@MapperScan("com.pcs.mapper")
@SpringBootApplication
public class OnlinePsychologicalCounselingPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlinePsychologicalCounselingPlatformApplication.class, args);
    }

}
