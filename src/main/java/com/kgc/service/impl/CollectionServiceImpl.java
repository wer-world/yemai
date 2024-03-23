package com.kgc.service.impl;

import com.kgc.dao.CollectionDao;
import com.kgc.entity.Collections;
import com.kgc.entity.Message;
import com.kgc.entity.User;
import com.kgc.service.CollectionService;
import com.kgc.util.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Message addCollection(Integer productId) {
        User user = ThreadLocalUtil.get();
        if (user==null){
            return Message.error("请登录后进行收藏!");
        }
        int affectRow = -1;
        //判断用户是否已经收藏过该商品
        affectRow = isCollection(productId);
        if (affectRow==1){
            return Message.error("该商品已在收藏夹，收藏失败！");
        }
        //获得用户收藏的数量
        int collectCount = collectionDao.getCollections(user.getId()).size();
        //判断收藏数量是否为6条，超过就删除用户最早收藏的商品
        if (collectCount==6){
            Collections collections = collectionDao.getFirstCollection(user.getId());
            collectionDao.deleteCollection(user.getId());
        }
        affectRow = collectionDao.addCollection(user.getId(), productId);
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
