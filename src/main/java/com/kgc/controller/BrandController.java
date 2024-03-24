package com.kgc.controller;

import com.kgc.entity.Brand;
import com.kgc.entity.Message;
import com.kgc.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 品牌管理控制类
 *
 * @Author: 魏小可
 * @Date: 2024-03-24-11:02
 */
@RestController
@RequestMapping("brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    @GetMapping("getBrandAllList")
    public Message getBrandAllList() {
        List<Brand> brandAllList = brandService.getBrandAllList();
        if (brandAllList != null) {
            return Message.success(brandAllList);
        }
        return Message.error();
    }
}
