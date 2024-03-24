package com.kgc.dao;

import com.kgc.entity.Brand;

import java.util.List;

/**
 * 品牌表操作接口类
 *
 * @Author: 魏小可
 * @Date: 2024-03-24-11:00
 */
public interface BrandDao {
    /**
     * 获取所有品牌
     *
     * @return 返回品牌集合
     */
    List<Brand> getBrandAllList();
}
