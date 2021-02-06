package com.leyou.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/5 7:01 下午
 */

//解决跨域问题
@Configuration
public class LeyouCorsConfiguration {

    @Bean
    public CorsFilter corsFilter() {

//        初始化配置类
        CorsConfiguration configuration = new CorsConfiguration();
//        设置允许的域
        configuration.addAllowedOrigin("http://manage.leyou.com");
//        是否允许携带cookie
        configuration.setAllowCredentials(true);
//        允许所有请求方法
        configuration.addAllowedMethod("*");
//        允许携带任何头信息
        configuration.addAllowedHeader("*");


        UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();

        configurationSource.registerCorsConfiguration("/**", configuration);
        CorsFilter corsFilter = new CorsFilter(configurationSource);
        return corsFilter;
    }

}
