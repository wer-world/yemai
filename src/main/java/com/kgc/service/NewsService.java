package com.kgc.service;

import com.kgc.entity.Message;
import com.kgc.entity.News;

import java.util.List;
import java.util.Map;

/**
 * 新闻资讯列表类
 */
public interface NewsService {
    /**
     * 资讯列表分页
     * @param map 分页所需的元素
     * @return
     */
    Message getNewsList(Map map);

    /**
     * 获取新闻资讯总数
     * @return
     */
    Message getNewsTotalCount(String title);

    /**
     * 添加资讯
     * @param news 添加资讯所需的参数
     * @return
     */
    Message addNews(News news);
    /**
     * 删除资讯
     * @param id 删除哪个资讯
     * @return
     */
    Message delNewsById(int id);
    /**
     * 修改资讯
     * @param news 修改咨询内容
     * @return
     */
    Message modifyNewsById(News news);
    /**
     * 根据id获取对应的资讯信息
     * @param id
     * @return
     */
    Message getNewsById(int id);
    /**
     * 修改时校验标题不能重复
     * @param title
     * @return
     */
    Message getNewsByTitle(String title);
}
