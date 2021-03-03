package com.leyou.comments.config;

import com.leyou.comments.properties.IdWorkerProperties;
import com.leyou.comments.utils.IdWorker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/27 10:44 下午
 */

@Configuration
//？？？先注掉这个注解
//@EnableConfigurationProperties(IdWorkerProperties.class)
public class IdWorkerConfig {

    @Bean
    public IdWorker idWorker(IdWorkerProperties prop) {
//        构建ID只需要这两个参数
        return new IdWorker(prop.getWorkerId(), prop.getDataCenterId());
    }
}
