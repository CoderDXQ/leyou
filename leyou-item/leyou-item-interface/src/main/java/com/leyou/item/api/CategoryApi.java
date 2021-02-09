package com.leyou.item.api;

import com.leyou.item.pojo.Category;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/5 6:11 下午
 */

@RequestMapping("category")
public interface CategoryApi {


    @GetMapping("list")
    public List<Category> queryCategoryByPid(@RequestParam(value = "pid", defaultValue = "0") Long pid);

    @GetMapping
    public List<String> queryNamesByIds(@RequestParam("ids") List<Long> ids);
}
