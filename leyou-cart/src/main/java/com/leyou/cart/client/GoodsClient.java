package com.leyou.cart.client;

import com.leyou.item.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/19 2:17 上午
 */
@FeignClient("item-service")
public interface GoodsClient extends GoodsApi {
}
