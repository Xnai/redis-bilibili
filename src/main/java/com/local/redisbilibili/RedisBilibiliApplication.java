package com.local.redisbilibili;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@MapperScan("com.local.redisbilibili.mapper")
public class RedisBilibiliApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisBilibiliApplication.class, args);
    }

}
