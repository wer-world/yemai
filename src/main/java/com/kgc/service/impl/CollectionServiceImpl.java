package com.kgc.service.impl;

import com.kgc.dao.CollectionDao;
import com.kgc.entity.Collections;
import com.kgc.entity.Message;
import com.kgc.entity.User;
import com.kgc.service.CollectionService;
import com.kgc.util.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author:25378
 * @DATE:2024/3/19 11:21
 * @Description:
 */
@Service
public class CollectionServiceImpl implements CollectionService {
    @Autowired
    private CollectionDao collectionDao;
    @Override
    public Message addCollection(Collections collections) {
        int affectRow = isCollection(collections.getProductId());
        if (affectRow==1){
            return Message.error("该商品已在收藏夹，收藏失败！");
        }
        affectRow = collectionDao.addCollection(collections.getUserId(), collections.getProductId());
        if (affectRow<1){
            return Message.error("添加收藏失败!");
        }
        return Message.success("收藏成功！");
    }

    @Override
    public Integer isCollection(Integer productId) {
        return collectionDao.isCollection(productId);
    }
}
