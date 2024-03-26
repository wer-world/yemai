package com.kgc.controller;

import com.kgc.entity.Message;
import com.kgc.entity.News;
import com.kgc.entity.Pages;
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
    public Message getNewsList(Pages page) {
        if (page == null) {
            return Message.error();
        }
        int form = (page.getCurrentPage() - 1) * page.getPageSize();
        Message getNewsTotalCount = newsService.getNewsTotalCount();
        long count = (Long) getNewsTotalCount.getData();
        page.setTotalCount(count);
        Map map = new HashMap<>();
        map.put("form", form);
        map.put("page", page);
        Message message = newsService.getNewsList(map);
        map.put("getNewsList", message.getData());

        return Message.success(map);
    }

    @RequestMapping("addNews")
    public Message addNews(@RequestBody Map<String, Object> map) {
        String title = (String) map.get("title");
        String content = (String) map.get("content");
        if (title == null || title.isEmpty()) {
            return Message.error("请输入标题");
        }
        if (content == null || content.isEmpty()) {
            return Message.error("请输入内容");
        }
        News news = new News();
        news.setTitle(title);
        news.setContent(content);
        return newsService.addNews(news);
    }

    @RequestMapping("modifyNewsById")
    public Message modifyNewsById(@RequestBody Map<String, Object> map) {
        String id = (String) map.get("id");
        String title = (String) map.get("title");
        String content = (String) map.get("content");
        if (title == null || title.isEmpty()) {
            return Message.error("请输入标题");
        }
        if (content == null || content.isEmpty()) {
            return Message.error("请输入内容");
        }
        News news = new News();
        news.setTitle(title);
        news.setContent(content);
        news.setId(Integer.parseInt(id));
        return newsService.modifyNewsById(news);
    }

    @RequestMapping("delNewsById")
    public Message delNewsById(String id) {
        if (id == null) {
            return Message.error();
        }
        int idInt = Integer.parseInt(id);
        return newsService.delNewsById(idInt);
    }

    @RequestMapping("getNewsById")
    public Message getNewsById(String id) {
        if (id == null) {
            return Message.error();
        }
        int idInt = Integer.parseInt(id);
        return newsService.getNewsById(idInt);
    }

    @RequestMapping("getNewsByTitle")
    public Message getNewsByTitle(@RequestBody Map<String, Object> map) {
        String title = (String) map.get("title");
        if (title == null){
            return Message.error("请输入标题");
        }
        return newsService.getNewsByTitle(title);
    }
}
