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

    @ExceptionHandler(value = ServiceException.class)
    public Message serviceExceptionHandler(ServiceException e) {
        logger.error("GlobalExceptionHandler serviceExceptionHandler error message:" + e.getMsg());
        return Message.error(e.getMsg());
    }
}
