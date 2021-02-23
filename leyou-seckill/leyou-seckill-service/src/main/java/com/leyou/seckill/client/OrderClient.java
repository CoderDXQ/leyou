package com.leyou.seckill.client;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/23 11:32 下午
 */

//???  需要改leyou-order服务
@FeignClient(value = "")
public interface OrderClient {
}
