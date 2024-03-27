package com.kgc.service.impl;

import com.kgc.dao.NewsDao;
import com.kgc.entity.Message;
import com.kgc.entity.News;
import com.kgc.enums.NewsExceptionEnum;
import com.kgc.exception.ServiceException;
import com.kgc.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class NewsServiceImpl implements NewsService {
    @Autowired
    private NewsDao newsDao;

    @Override
    public Message getNewsList(Map map) {
        List<News> newsList = newsDao.getNewsList(map);
        if (newsList != null && !newsList.isEmpty()) {
            return Message.success(newsList);
        }
        return Message.error();
    }

    @Override
    public Message getNewsTotalCount(String title) {
        long count = 0;
        count = newsDao.getNewsTotalCount(title);
        if (count != 0) {
            return Message.success(count);
        }
        return Message.error();
    }

    @Override
    @Transactional
    public Message addNews(News news) {
        int count = newsDao.addNews(news);
        if (count != 1) {
            throw new ServiceException("NewsServiceImpl addNews " + NewsExceptionEnum.NEWS_ADD_FAILURE.getMessage(), NewsExceptionEnum.NEWS_ADD_FAILURE.getMsg());
        }
        return Message.success(count);
    }

    @Override
    @Transactional
    public Message delNewsById(int id) {
        int count = newsDao.delNewsById(id);
        if (count != 1) {
            throw new ServiceException("NewsServiceImpl delNewsById " + NewsExceptionEnum.NEWS_DELETE_FAILURE.getMessage(), NewsExceptionEnum.NEWS_DELETE_FAILURE.getMsg());
        }
        return Message.success(count);
    }

    @Override
    @Transactional
    public Message modifyNewsById(News news) {
        int count = newsDao.modifyNewsById(news);
        if (count != 1) {
            throw new ServiceException("NewsServiceImpl modifyNewsById " + NewsExceptionEnum.NEWS_UPDATE_FAILURE.getMessage(), NewsExceptionEnum.NEWS_UPDATE_FAILURE.getMsg());
        }
        return Message.success(count);
    }

    @Override
    public Message getNewsById(int id) {
        News news = newsDao.getNewsById(id);
        return Message.success(news);
    }

    @Override
    public Message getNewsByTitle(String title) {
        News news = newsDao.getNewsByTitle(title);
        if (news != null) {
            return Message.error("该标题已存在");
        }
        return Message.success();
    }
}
