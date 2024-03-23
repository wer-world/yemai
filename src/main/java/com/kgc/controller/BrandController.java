package com.kgc.controller;

import com.kgc.entity.Message;
import com.kgc.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    @GetMapping("getBrandList")
    public Message getBrandList() {
        return brandService.getBrandList();
    }
}
