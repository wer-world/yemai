package com.kgc.dao;

import com.kgc.entity.News;

import java.util.List;
import java.util.Map;

public interface NewsDao {
    /**
     * 获取新闻资讯列表
     *
     * @return 返回新闻资讯列表
     */
    /**
     * 新闻资讯分页
     * @param map 所需的条件
     * @return
     */
    public List<News> getNewsList(Map map);

    /**
     * 获取新闻资讯总数
     * @return
     */
    public long getNewsTotalCount(String title);

    /**
     * 添加资讯
     * @param news 添加资讯所需的参数
     * @return
     */
    public int addNews(News news);

    /**
     * 删除资讯
     * @param id 删除哪个资讯
     * @return
     */
    public int delNewsById(int id);

    /**
     * 修改资讯
     * @param news 修改咨询内容
     * @return
     */
    public int modifyNewsById(News news);

    /**
     * 根据id获取对应的资讯信息
     * @param id
     * @return
     */
    public News getNewsById(int id);

    /**
     * 修改时校验标题不能重复
     * @param title
     * @return
     */
    public News getNewsByTitle(String title);
}
