package com.leyou.goods.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/16 10:45 上午
 */

@Controller
public class GoodsController {

    @GetMapping("item/{id}.html")
    public String toItemPage(@PathVariable("id") Long id, Model model) {


        return "item";
    }
}
