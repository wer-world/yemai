package com.kgc.service.impl;

import com.kgc.dao.BrandDao;
import com.kgc.entity.Message;
import com.kgc.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandDao brandDao;
    @Override
    public Message getBrandList() {
        return Message.success(brandDao.getBrandList());
    }
}
