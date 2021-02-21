package com.leyou.seckill.vo;

import com.leyou.common.pojo.UserInfo;
import com.leyou.item.pojo.SeckillGoods;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/21 10:16 下午
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeckillMessage {

    //    用户信息
    private UserInfo userInfo;

    //    秒杀商品
    private SeckillGoods seckillGoods;
}
