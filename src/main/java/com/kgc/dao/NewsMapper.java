package com.kgc.dao;

import com.kgc.entity.News;

import java.util.List;

public interface NewsMapper {
    /**
     * 获取新闻资讯列表
     *
     * @return 返回新闻资讯列表
     */
    List<News> getNewsList();
}
