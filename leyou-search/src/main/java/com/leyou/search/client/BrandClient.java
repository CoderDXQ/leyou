package com.leyou.search.client;

import com.leyou.item.api.BrandApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/10 1:13 上午
 */

@FeignClient(value = "item-service")
public interface BrandClient extends BrandApi {
}
