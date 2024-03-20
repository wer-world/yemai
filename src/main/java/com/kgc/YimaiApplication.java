package com.kgc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@MapperScan(basePackages = "com.kgc.dao")
@EnableScheduling
@SpringBootApplication
public class YimaiApplication {

    public static void main(String[] args) {
        SpringApplication.run(YimaiApplication.class, args);
    }

}
