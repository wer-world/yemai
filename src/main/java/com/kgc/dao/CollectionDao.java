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
    public Integer addCollection(@Param("userId") Integer userId,@Param("productId") Integer productId);

    public Integer isCollection(@Param("productId") Integer productId);

    List<Collections> getCollections(Integer userId);

    Integer deleteCollection(Integer id);

    Collections getFirstCollection(Integer userId);
}
