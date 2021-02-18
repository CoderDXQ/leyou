package com.leyou.auth.client;

import com.leyou.user.api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/18 5:05 下午
 */

//@FeignClient(value = "user-service")
@FeignClient("user-service")
public interface UserClient extends UserApi {
}
