package com.kgc.service;

import com.kgc.entity.Message;
import com.kgc.entity.News;

import java.util.List;

public interface NewsService {
    /**
     * 获取新闻资讯列表
     * @return 返回消息类
     */
    Message getNewsList();
}
