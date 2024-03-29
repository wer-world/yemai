package com.kgc.dao;

import com.kgc.entity.Collections;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author:25378
 * @DATE:2024/3/19 11:07
 * @Description:
 */
public interface CollectionDao {
    Integer addCollection(@Param("userId") Integer userId,@Param("productId") Integer productId);

    /**
     * 用来查询是否已经收藏，被addCollection所调用
     *
     * @param collections
     * @return
     */
    Integer isCollection(Collections collections);

    List<Collections> getCollections(Integer userId);

    Integer deleteCollection(Integer id);

    Collections getFirstCollection(Integer userId);
}
