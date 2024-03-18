package com.kgc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "com.kgc.dao")
@SpringBootApplication
public class YimaiApplication {

    public static void main(String[] args) {
        SpringApplication.run(YimaiApplication.class, args);
    }

}
