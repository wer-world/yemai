package com.kgc.init;

import com.kgc.config.EasyBuyInitConfig;
import com.kgc.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 初始化商品信息到ES中信息
 *
 * @Author: 魏小可
 * @Date: 2024-03-19-10:24
 */
@Component
public class InitProductESCommandLineRunner implements CommandLineRunner {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private EasyBuyInitConfig easyBuyInitConfig;

    @Autowired
    private ProductService productService;

    @Override
    public void run(String... args) throws Exception {
        if (!easyBuyInitConfig.getInitEsData()) {
            logger.info("InitProductESCommandLineRunner run product to es init cancel");
            return;
        }
        logger.info("InitProductESCommandLineRunner run product to es init start...");
        Boolean isInitSuccess = productService.saveProductListToEs();
        if (isInitSuccess) {
            logger.info("InitProductESCommandLineRunner run product to es init success");
        } else {
            logger.error("InitProductESCommandLineRunner run product to es init error");
        }
    }
}
