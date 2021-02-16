package com.leyou.goods.client;

import com.leyou.item.api.CategoryApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/10 1:14 上午
 */

@FeignClient(value = "item-service")
public interface CategoryClient extends CategoryApi {
}
