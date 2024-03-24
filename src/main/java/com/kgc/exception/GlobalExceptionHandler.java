package com.kgc.exception;

import com.kgc.entity.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.WebApplicationContext;

/**
 * 全局异常处理类
 *
 * @Author: 魏小可
 * @Date: 2024-03-18-11:48
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private WebApplicationContext applicationContext;

    @ExceptionHandler(value = ServiceException.class)
    public Message serviceExceptionHandler(ServiceException e) {
        logger.error("GlobalExceptionHandler serviceExceptionHandler error message:" + e.getMsg());
        return Message.error(e.getMsg());
    }

    @ExceptionHandler(InitException.class)
    public void initExceptionHandler(InitException e) {
        logger.error("GlobalExceptionHandler initExceptionHandler error message:" + e.getMessage() + ",error field:" + e.getField() + ",error value:" + e.getValue());
        // 初始化失败正常停止程序
        System.exit(SpringApplication.exit(applicationContext));
    }
}
