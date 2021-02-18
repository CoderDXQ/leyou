package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/18 11:14 下午
 */

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class LeyouCartApplication {
    public static void main(String[] args) {
        SpringApplication.run(LeyouCartApplication.class);
    }
}
