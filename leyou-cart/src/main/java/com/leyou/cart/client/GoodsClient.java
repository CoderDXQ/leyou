package com.leyou.cart.client;

import com.leyou.item.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/19 2:17 上午
 */
@FeignClient(value = "item-service")
//GoodsApi是item模块提供的暴露接口 其他模块想使用item模块的Controller方法只需要写一个Client类继承GoodsApi即可，后面的事情Feign会处理
public interface GoodsClient extends GoodsApi {

}
