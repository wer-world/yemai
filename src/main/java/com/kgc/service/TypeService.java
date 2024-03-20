package com.kgc.service;

import com.kgc.entity.Message;
import com.kgc.entity.Type;

import java.util.List;

/**
 * @Author:25378
 * @DATE:2024/3/20 20:07
 * @Description:
 */
public interface TypeService {
    /**
     * 查询所有用户种类(超管，管理，用户)
     * @return
     */
    public Message getTypeList();
}
