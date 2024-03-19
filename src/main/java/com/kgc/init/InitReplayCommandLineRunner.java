package com.kgc.init;

import com.kgc.util.ReplayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 重放攻击初始化类
 *
 * @Author: 魏小可
 * @Date: 2024-03-19-10:18
 */
@Component
public class InitReplayCommandLineRunner implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ReplayUtil replayUtil;

    @Override
    public void run(String... args) throws Exception {
        if (!replayUtil.isInitReplay()) {
            logger.info("InitReplayCommandLineRunner run replay defense init cancel");
            return;
        }
        logger.info("InitReplayCommandLineRunner run replay defense init start...");
        replayUtil.createUUIDToRedis();
        logger.info("InitReplayCommandLineRunner run replay defense init success");

    }
}