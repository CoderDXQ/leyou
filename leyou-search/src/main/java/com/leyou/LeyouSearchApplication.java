package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/9 11:24 下午
 */

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class LeyouSearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(LeyouSearchApplication.class, args);
    }
}
