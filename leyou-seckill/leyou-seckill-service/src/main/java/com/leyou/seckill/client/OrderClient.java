package com.leyou.seckill.client;

import com.leyou.order.api.OrderApi;
import com.leyou.order.pojo.Order;
import com.leyou.seckill.config.OrderConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/23 11:32 下午
 */

//???  需要改leyou-order服务
@FeignClient(value = "order-service", configuration = OrderConfig.class)
public interface OrderClient extends OrderApi {

}
