package com.kgc.exception;

import com.kgc.entity.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理类
 *
 * @Author: 魏小可
 * @Date: 2024-03-18-11:48
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(LoginException.class)
    public Message loginExceptionHandler(LoginException e) {
        return Message.loginError(e.getMsg());
    }

    @ExceptionHandler(ServiceException.class)
    public Message serviceExceptionHandler(ServiceException e) {
        logger.error("GlobalExceptionHandler serviceExceptionHandler error message:" + e.getMessage());
        return Message.error(e.getMsg());
    }

    @ExceptionHandler(NumberFormatException.class)
    public Message numberFormatExceptionHandler(NumberFormatException e) {
        logger.error("GlobalExceptionHandler numberFormatExceptionHandler error message:" + e.getMessage());
        return Message.error("数字参数传错啦!请输入正确数字!OvO");
    }
}
