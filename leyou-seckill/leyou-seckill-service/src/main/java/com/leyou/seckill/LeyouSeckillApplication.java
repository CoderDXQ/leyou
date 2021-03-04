package com.leyou.seckill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import tk.mybatis.spring.annotation.MapperScan;


/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/21 10:45 下午
 */

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
//@SpringBootApplication
//@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@EnableFeignClients
@EnableDiscoveryClient
@MapperScan("com.leyou.seckill.mapper")
public class LeyouSeckillApplication {
    public static void main(String[] args) {
        SpringApplication.run(LeyouSeckillApplication.class, args);
    }
}
