package com.kgc.controller;

import com.alibaba.fastjson.JSON;
import com.kgc.entity.Message;
import com.kgc.entity.News;
import com.kgc.entity.Page;
import com.kgc.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("news")
public class NewsController {
    @Autowired
    private NewsService newsService;

    @RequestMapping("getNewsList")
    public Message getNewsList(Page page) {
        if (page == null){
            return Message.error();
        }
        int form = (page.getCurrentPage()-1)*page.getPageSize();
        Message getNewsTotalCount = newsService.getNewsTotalCount();
        long count = (Long) getNewsTotalCount.getData();
        page.setTotalCount(count);
        Map map = new HashMap<>();
        map.put("form",form);
        map.put("page",page);
        Message message = newsService.getNewsList(map);
        map.put("getNewsList",message.getData());

        return Message.success(map);
    }
    @RequestMapping("addNews")
    public Message addNews(@RequestBody Map map) {
        String title =(String) map.get("title");
        String content =(String)map.get("content");
        News news = new News();
        news.setTitle(title);
        news.setContent(content);
        return newsService.addNews(news);
    }

    @RequestMapping("modifyNewsById")
    public Message modifyNewsById(@RequestBody Map map) {
        String id = (String)map.get("id");
        String title =(String) map.get("title");
        String content =(String)map.get("content");
        News news = new News();
        news.setTitle(title);
        news.setContent(content);
        news.setId(Integer.parseInt(id));
        return newsService.modifyNewsById(news);
    }

    @RequestMapping("delNewsById")
    public Message delNewsById(String id) {
        if (id == null){
            return Message.error();
        }
        int idInt = Integer.parseInt(id);
        return newsService.delNewsById(idInt);
    }

    @RequestMapping("getNewsById")
    public Message getNewsById(String id) {
        if (id == null){
            return Message.error();
        }
        int idInt = Integer.parseInt(id);
        return newsService.getNewsById(idInt);
    }

    @RequestMapping("getNewsByTitle")
    public Message getNewsByTitle(@RequestBody Map map) {
        String title=(String) map.get("title");
        return newsService.getNewsByTitle(title);
    }
}
