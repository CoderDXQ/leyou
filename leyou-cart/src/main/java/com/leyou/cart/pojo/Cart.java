package com.leyou.cart.pojo;

import lombok.Data;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/19 1:47 上午
 */

@Data
public class Cart {
    private Long userId;// 用户id
    private Long skuId;// 商品id
    private String title;// 标题
    private String image;// 图片
    private Long price;// 加入购物车时的价格
    private Integer num;// 购买数量
    private String ownSpec;// 商品规格参数
}
