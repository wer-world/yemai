package com.kgc.dao;

import org.apache.ibatis.annotations.Param;

/**
 * @Author:25378
 * @DATE:2024/3/19 11:07
 * @Description:
 */
public interface CollectionDao {
    public Integer addCollection(@Param("userId") Integer userId,@Param("productId") Integer productId);

    public Integer isCollection(@Param("productId") Integer productId);
}
