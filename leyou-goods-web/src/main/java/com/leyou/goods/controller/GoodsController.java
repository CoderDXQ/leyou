package com.leyou.goods.controller;

import com.leyou.goods.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/16 10:45 上午
 */

@Controller
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @GetMapping("item/{id}.html")
    public String toItemPage(@PathVariable("id") Long id, Model model) {

//        加载所需要的数据
        Map<String, Object> map = this.goodsService.loadData(id);

//        放入模型
        model.addAllAttributes(map);


        return "item";
    }
}
