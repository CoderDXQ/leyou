package com.leyou.seckill.client;

import com.leyou.item.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/23 11:32 下午
 */
@FeignClient(value = "item-service")
public interface GoodsClient extends GoodsApi {
}
