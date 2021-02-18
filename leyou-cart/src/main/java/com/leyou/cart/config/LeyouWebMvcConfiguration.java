package com.leyou.cart.config;

import com.leyou.cart.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/19 1:29 上午
 */

@Configuration
public class LeyouWebMvcConfiguration implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        添加拦截规则 添加拦截器实现类和拦截路径
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**");//拦截所有路径
    }
}
