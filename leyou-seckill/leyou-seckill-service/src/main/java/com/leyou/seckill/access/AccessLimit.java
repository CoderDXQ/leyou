package com.leyou.seckill.access;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * 接口限流注解 自定义注解
 * @date 2021/2/23 11:31 下午
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AccessLimit {

    //    限流时间
    int seconds();

    //    最大请求次数
    int maxCount();

    //    是否需要登录
    boolean needLogin() default true;
}
