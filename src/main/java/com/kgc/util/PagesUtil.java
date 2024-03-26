package com.kgc.util;

import com.kgc.entity.Pages;

import java.util.Map;

/**
 * 页面对象生成工具类
 *
 * @Author: 魏小可
 * @Date: 2024-03-25-14:54
 */
public class PagesUtil {
    public static Pages parseMapToPages(Map<String, Object> params) {
        String currentPageStr = params.get("currentPage").toString();
        String pageSizeStr = params.get("pageSize").toString();
        int currentPage = 1;
        int pageSize = 5;
        if (currentPageStr != null && !currentPageStr.isEmpty()) {
            currentPage = Integer.parseInt(currentPageStr);
        }
        if (pageSizeStr != null && !pageSizeStr.isEmpty()) {
            pageSize = Integer.parseInt(pageSizeStr);
        }
        Pages pages = new Pages();
        pages.setCurrentPage(currentPage);
        pages.setPageSize(pageSize);
        return pages;
    }
}
