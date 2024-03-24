package com.kgc.service;

import com.kgc.entity.Brand;

import java.util.List;

/**
 * 品牌业务接口类
 *
 * @Author: 魏小可
 * @Date: 2024-03-24-10:56
 */
public interface BrandService {
    /**
     * 获取所有品牌
     *
     * @return 返回品牌集合
     */
    List<Brand> getBrandAllList();
}
