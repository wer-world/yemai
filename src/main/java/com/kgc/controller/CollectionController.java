package com.kgc.controller;

import com.kgc.entity.Collections;
import com.kgc.entity.Message;
import com.kgc.entity.User;
import com.kgc.service.CollectionService;
import com.kgc.util.ThreadLocalUtil;
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
    public Message addCollection(@RequestBody Map<String, Object> params) {
        User user = ThreadLocalUtil.get();
        Integer productId = Integer.valueOf(params.get("productId").toString());
        if (productId == null) {
            return Message.error();
        }
        Collections collections = new Collections();
        collections.setUserId(user.getId());
        collections.setProductId(productId);
        return collectionService.addCollection(collections);
    }
}
