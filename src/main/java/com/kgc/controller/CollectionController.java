package com.kgc.controller;

import com.kgc.entity.Message;
import com.kgc.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author:25378
 * @DATE:2024/3/19 11:33
 * @Description:
 */
@RestController
@RequestMapping("collection")
public class CollectionController {
    @Autowired
    private CollectionService collectionService;

    @RequestMapping("addCollection")
    public Message addCollection(@RequestBody Map<String, Object> map){
        Integer productId = Integer.parseInt(map.get("productId").toString());
        return collectionService.addCollection(productId);
    }
}
