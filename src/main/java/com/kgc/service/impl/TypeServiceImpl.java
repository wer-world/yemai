package com.kgc.service.impl;

import com.kgc.dao.TypeDao;
import com.kgc.entity.Message;
import com.kgc.entity.Type;
import com.kgc.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author:25378
 * @DATE:2024/3/20 20:08
 * @Description:
 */
@Service
public class TypeServiceImpl implements TypeService {
    @Autowired
    private TypeDao typeDao;
    @Override
    public Message getTypeList() {
        List<Type> typeList = typeDao.getTypeList();
        return Message.success(typeList);
    }
}
