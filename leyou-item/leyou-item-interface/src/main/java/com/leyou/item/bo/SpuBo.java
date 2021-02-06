package com.leyou.item.bo;

import com.leyou.item.pojo.Spu;
import lombok.Data;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/7 1:01 上午
 */

@Data
public class SpuBo extends Spu {
    String cname;// 商品分类名称
    String bname;// 品牌名称
}
