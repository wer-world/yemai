package com.kgc.service;

import com.kgc.entity.Collections;
import com.kgc.entity.Message;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author:25378
 * @DATE:2024/3/19 11:19
 * @Description:
 */
public interface CollectionService {
    /**
     * 用来添加收藏
     * 前端需传入productId
     * @param collections
     * @return
     */
    Message addCollection(Collections collections);

    /**
     * 用来查询是否已经收藏，被addCollection所调用
     * @param productId
     * @return
     */
    Integer isCollection(@Param("productId") Integer productId);

    /**
     * 查询用户的所有收藏
     * @param userId
     * @return
     */
    Message getCollections(Integer userId);

    /**
     * 删除收藏
     * @param id
     * @return
     */
    Message deleteCollection(Integer id);


}
