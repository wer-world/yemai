package com.kgc.controller;

import com.kgc.entity.Message;
import com.kgc.entity.User;
import com.kgc.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author:25378
 * @DATE:2024/3/20 20:10
 * @Description:
 */
@RestController
@RequestMapping("type")
public class TypeController {
    @Autowired
    private TypeService typeService;

    @RequestMapping("getTypeList")
    public Message getTypeList(){
        return typeService.getTypeList();
    }

}
