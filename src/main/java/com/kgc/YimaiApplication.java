package com.kgc;

import com.kgc.util.ReplayUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class YimaiApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(YimaiApplication.class, args);
        ReplayUtil replayUtil = context.getBean(ReplayUtil.class);
        replayUtil.createUUIDToRedis();
    }

}
