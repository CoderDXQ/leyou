package com.leyou.search.controller;

import com.leyou.common.pojo.PageResult;
import com.leyou.search.pojo.Goods;
import com.leyou.search.pojo.SearchRequest;
import com.leyou.search.pojo.SearchResult;
import com.leyou.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/10 10:48 上午
 */


//需要写个请求地址验证一下   这里没有验证好 包括service也需要好好看看
@Controller
@CrossOrigin //解决跨域问题
public class SearchController {

    @Autowired
    private SearchService searchService;

    @PostMapping("page")
    public ResponseEntity<SearchResult> search(@RequestBody SearchRequest request) {
        SearchResult result = this.searchService.search(request);

        if (request == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(result);
    }


}
