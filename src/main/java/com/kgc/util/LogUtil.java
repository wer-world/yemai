package com.kgc.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 日志增强类
 *
 * @Author: 魏小可
 * @Date: 2024-03-16-22:27
 */
@Aspect
@Component
public class LogUtil {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut(value = "execution(* com.kgc..*.*(..))")
    public void pointCut(){}

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint){
        try {
            logger.info("Log around class:" + joinPoint.getSignature() + " start...");
            logger.debug("Log around class:" + joinPoint.getSignature() + ",method:" + joinPoint.getTarget() + ",method params:" + Arrays.toString(joinPoint.getArgs()));
            Object result = joinPoint.proceed();
            logger.debug("Log around class:" + joinPoint.getSignature() + ",method:" + joinPoint.getTarget() + ",method params:" + Arrays.toString(joinPoint.getArgs()) + ",result:" + result);
            logger.info("Log around class:" + joinPoint.getSignature() + " end...");
            return result;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
