package com.kgc.service;

import com.kgc.entity.Collections;
import com.kgc.entity.Message;
import org.apache.ibatis.annotations.Param;

/**
 * @Author:25378
 * @DATE:2024/3/19 11:19
 * @Description:
 */
public interface CollectionService {
    /**
     * 用来添加收藏
     * 前端需传入productId
     * @param productId
     * @return
     */
    Message addCollection(Collections productId);

    /**
     * 用来查询是否已经收藏，被addCollection所调用
     * @param productId
     * @return
     */
    Integer isCollection(@Param("productId") Integer productId);

}
