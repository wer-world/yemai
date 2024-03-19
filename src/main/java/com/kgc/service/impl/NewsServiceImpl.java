package com.kgc.service.impl;

import com.kgc.dao.NewsMapper;
import com.kgc.entity.Message;
import com.kgc.entity.News;
import com.kgc.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {
    @Autowired
    private NewsMapper newsMapper;
    @Override
    public Message getNewsList() {
        List<News> newsList = newsMapper.getNewsList();
        Message message = new Message("200","success",newsList);
        return message;
    }
}