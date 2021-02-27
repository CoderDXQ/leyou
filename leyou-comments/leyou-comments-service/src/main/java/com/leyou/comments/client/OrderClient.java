package com.leyou.comments.client;

import com.leyou.order.api.OrderApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/27 10:20 下午
 */
@FeignClient(value = "order-service")
public interface OrderClient extends OrderApi {
}
