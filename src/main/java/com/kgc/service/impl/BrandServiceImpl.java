package com.kgc.service.impl;

import com.kgc.dao.BrandDao;
import com.kgc.entity.Brand;
import com.kgc.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 品牌业务接口实现类
 *
 * @Author: 魏小可
 * @Date: 2024-03-24-10:58
 */
@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandDao brandDao;

    @Override
    public List<Brand> getBrandAllList() {
        List<Brand> brandList = brandDao.getBrandAllList();
        if (brandList != null && !brandList.isEmpty()) {
            return brandList;
        }
        return null;
    }
}
