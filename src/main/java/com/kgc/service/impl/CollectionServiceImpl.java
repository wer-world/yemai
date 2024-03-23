package com.kgc.service.impl;

import com.kgc.dao.CollectionDao;
import com.kgc.entity.Collections;
import com.kgc.entity.Message;
import com.kgc.service.CollectionService;
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

    @Override
    public Message getCollections(Integer userId) {
        List<Collections> collections = collectionDao.getCollections(userId);
        return Message.success(collections);
    }

    @Override
    public Message deleteCollection(Integer id) {
        Integer affectRow = collectionDao.deleteCollection(id);
        if (affectRow<1){
            return Message.error("删除失败！");
        }
        return Message.success("删除成功！");
    }
}
