package com.kgc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@MapperScan(basePackages = "com.kgc.dao")
@EnableScheduling
@EnableTransactionManagement
@SpringBootApplication
public class YimaiApplication {

    public static void main(String[] args) {
        SpringApplication.run(YimaiApplication.class, args);
    }

}
