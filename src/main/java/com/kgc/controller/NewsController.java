package com.kgc.controller;

import com.kgc.entity.Message;
import com.kgc.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("news")
public class NewsController {
    @Autowired
    private NewsService newsService;

    @RequestMapping("getNewsList")
    public Message getNewsList(){
        return newsService.getNewsList();
    }
}
