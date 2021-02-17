package com.leyou.search.pojo;

import lombok.Data;

import java.util.Map;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/10 10:39 上午
 */

//search服务请求类
public class SearchRequest {
    private String key;// 搜索条件

    private Integer page;// 当前页

    private Map<String, Object> filter;

    public SearchRequest() {
    }

    public SearchRequest(Map<String, Object> filter) {
        this.filter = filter;
    }

    public SearchRequest(String key, Integer page, Map<String, Object> filter) {
        this.key = key;
        this.page = page;
        this.filter = filter;
    }

    public Map<String, Object> getFilter() {
        return filter;
    }

    private static final Integer DEFAULT_SIZE = 20;// 每页大小，不从页面接收，而是固定大小
    private static final Integer DEFAULT_PAGE = 1;// 默认页

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getPage() {
        if (page == null) {
            return DEFAULT_PAGE;
        }
        // 获取页码时做一些校验，不能小于1
        return Math.max(DEFAULT_PAGE, page);
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return DEFAULT_SIZE;
    }
}

