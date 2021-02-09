package com.leyou.search.service;

import com.leyou.item.pojo.Spu;
import com.leyou.search.pojo.Goods;
import org.springframework.stereotype.Service;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/10 1:19 上午
 */

@Service
public class SearchService {

    public Goods buildGoods(Spu spu) {
        Goods goods = new Goods();

        goods.setId(spu.getId());
        goods.setCid1(spu.getCid1());
        goods.setCid2(spu.getCid2());
        goods.setCid3(spu.getCid3());

        return goods;
    }








}
