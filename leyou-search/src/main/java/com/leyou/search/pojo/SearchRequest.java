package com.leyou.search.pojo;

import lombok.Data;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/10 10:39 上午
 */

//search服务请求类
public class SearchRequest {
    private String key;// 搜索条件

    private Integer page;// 当前页


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

