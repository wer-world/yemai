package com.kgc.service.impl;

import com.kgc.dao.NewsDao;
import com.kgc.entity.Message;
import com.kgc.entity.News;
import com.kgc.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class NewsServiceImpl implements NewsService {
    @Autowired
    private NewsDao newsMapper;
    @Override
    public Message getNewsList(Map map) {
        List<News> newsList = newsMapper.getNewsList(map);
        if (newsList != null&&newsList.size() != 0){
            return Message.success(newsList);
        }
        return Message.error();
    }

    @Override
    public Message getNewsTotalCount() {
        long count = 0;
        count =  newsMapper.getNewsTotalCount();
        if (count != 0){
            return Message.success(count);
        }
        return Message.error();
    }

    @Override
    public Message addNews(News news) {
        if (news.getTitle() == null || "".equals(news.getTitle())){
            return Message.error("请输入标题");
        }
        if (news.getContent() == null || "".equals(news.getContent())){
            return Message.error("请输入内容");
        }
        int count = 0;
        count = newsMapper.addNews(news);
        if (count != 1){
            return Message.error();
        }
        return Message.success(count);
    }

    @Override
    public Message delNewsById(int id) {
        int count = newsMapper.delNewsById(id);
        if (count != 1){
            return Message.error();
        }
        return Message.success(count);
    }

    @Override
    public Message modifyNewsById(News news) {
        if (news.getTitle() == null || "".equals(news.getTitle())){
            return Message.error("请输入标题");
        }
        if (news.getContent() == null || "".equals(news.getContent())){
            return Message.error("请输入内容");
        }
        int count = newsMapper.modifyNewsById(news);
        if (count != 1){
            return Message.error();
        }
        return Message.success(count);
    }

    @Override
    public Message getNewsById(int id) {
            News news = newsMapper.getNewsById(id);
        return Message.success(news);
    }

    @Override
    public Message getNewsByTitle(String title) {
        if (title == null){
            return Message.error("请输入标题");
        }
        News news = newsMapper.getNewsByTitle(title);
        if (news != null){
            return Message.error("该标题已存在");
        }
        return Message.success(news);
    }
}
