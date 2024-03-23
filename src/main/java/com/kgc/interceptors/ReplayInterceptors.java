package com.kgc.interceptors;

import com.kgc.config.ReplayConfig;
import com.kgc.enums.TokenExceptionEnum;
import com.kgc.exception.ServiceException;
import com.kgc.util.DateCheckUtil;
import com.kgc.util.ReplayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 重放攻击拦截器
 *
 * @Author: 魏小可
 * @Date: 2024-03-21-15:03
 */
@Component
public class ReplayInterceptors implements HandlerInterceptor {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ReplayUtil replayUtil;

    @Autowired
    private ReplayConfig replayConfig;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("ReplayInterceptors preHandle start...");
        if (replayConfig.getIsReplay()) {
            logger.debug("ReplayInterceptors preHandle url:" + request.getRequestURI());
            String urlTime = request.getHeader("urlTime"); // 时间戳
            String random = request.getHeader("random"); // 随机数
            if (urlTime == null || random == null || urlTime.isEmpty() || random.isEmpty()) {
                logger.error("ReplayInterceptors preHandle replay error");
                throw new ServiceException(TokenExceptionEnum.ILLEGAL_REQUEST.getMsg());
            }
            // 重放攻击校验
            // 时间戳校验
            boolean checkUrlTime = DateCheckUtil.checkDateTime(urlTime);
            // 随机数校验
            String checkRandom = replayUtil.checkRandom(random);
            if (!checkUrlTime || checkRandom == null) {
                logger.error("ReplayInterceptors preHandle replay error");
                throw new ServiceException(TokenExceptionEnum.ILLEGAL_REQUEST.getMsg());
            }
        }
        logger.info("LoginInterceptor preHandle end...");
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String random = request.getHeader("random");
        replayUtil.removeRandom(random);
    }
}
