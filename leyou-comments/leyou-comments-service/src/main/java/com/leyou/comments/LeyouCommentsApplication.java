package com.leyou.comments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/26 2:31 上午
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class LeyouCommentsApplication {
    public static void main(String[] args) {
        SpringApplication.run(LeyouCommentsApplication.class, args);
    }
}
