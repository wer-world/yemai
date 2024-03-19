package com.kgc;

import com.kgc.util.ReplayUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@MapperScan(basePackages = "com.kgc.dao")
@SpringBootApplication
public class YimaiApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(YimaiApplication.class, args);
        ReplayUtil replayUtil = context.getBean(ReplayUtil.class);
        replayUtil.createUUIDToRedis();
    }

}
