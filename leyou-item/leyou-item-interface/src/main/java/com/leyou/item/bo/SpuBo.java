package com.leyou.item.bo;

import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuDetail;
import lombok.Data;

import java.util.List;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/7 1:01 上午
 */

@Data
public class SpuBo extends Spu {
    private String cname;// 商品分类名称
    private String bname;// 品牌名称

    private SpuDetail SpuDetail;

    private List<Sku> skus;
}
