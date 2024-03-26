package com.kgc.service.impl;

import com.kgc.dao.CollectionDao;
import com.kgc.entity.Collections;
import com.kgc.entity.Message;
import com.kgc.enums.CollectionExceptionEnum;
import com.kgc.exception.ServiceException;
import com.kgc.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    @Transactional
    public Message addCollection(Collections collections) {
        int affectRow = collectionDao.isCollection(collections);
        if (affectRow == 1) {
            throw new ServiceException("CollectionServiceImpl addCollection " + CollectionExceptionEnum.PRODUCT_EXIST_ERROR.getMessage(), CollectionExceptionEnum.PRODUCT_EXIST_ERROR.getMsg());
        }
        //获得用户收藏的数量
        int collectCount = collectionDao.getCollections(collections.getUserId()).size();
        //判断收藏数量是否为6条，超过就删除用户最早收藏的商品
        if (collectCount == 6) {
            Collections firstCollections = collectionDao.getFirstCollection(collections.getUserId());
            collectionDao.deleteCollection(firstCollections.getId());
        }
        affectRow = collectionDao.addCollection(collections.getUserId(), collections.getProductId());
        if (affectRow < 1) {
            throw new ServiceException("CollectionServiceImpl addCollection " + CollectionExceptionEnum.COLLECTION_ADD_FAILURE.getMessage(), CollectionExceptionEnum.COLLECTION_ADD_FAILURE.getMsg());
        }
        return Message.success("收藏成功！");
    }

    @Override
    public Message getCollections(Integer userId) {
        List<Collections> collections = collectionDao.getCollections(userId);
        return Message.success(collections);
    }

    @Override
    @Transactional
    public Message deleteCollection(Integer id) {
        Integer affectRow = collectionDao.deleteCollection(id);
        if (affectRow < 1) {
            throw new ServiceException("CollectionServiceImpl addCollection " + CollectionExceptionEnum.COLLECTION_DELETE_FAILURE.getMessage(), CollectionExceptionEnum.COLLECTION_DELETE_FAILURE.getMsg());
        }
        return Message.success("删除成功！");
    }
}
