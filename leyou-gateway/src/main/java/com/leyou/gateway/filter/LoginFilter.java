package com.leyou.gateway.filter;

import com.leyou.common.utils.CookieUtils;
import com.leyou.common.utils.JwtUtils;
import com.leyou.gateway.config.FilterProperties;
import com.leyou.gateway.config.JwtProperties;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/18 8:49 下午
 */

@Component
@EnableConfigurationProperties({JwtProperties.class, FilterProperties.class})//启用配置类
public class LoginFilter extends ZuulFilter {

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private FilterProperties filterProperties;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 10;
    }

    @Override //返回ture就拦截 返回false就放过  所以实现白名单功能只要返回false即可
    public boolean shouldFilter() {
//        获取白名单
        List<String> allowPaths = this.filterProperties.getAllowPaths();
        //        初始化运行上下文
        RequestContext context = RequestContext.getCurrentContext();
//        获取request
        HttpServletRequest request = context.getRequest();
//        获取请求的路径
        String url = request.getRequestURL().toString();

        for (String allowpath : allowPaths) {
            if (StringUtils.contains(url, allowpath)) {
                return false;
            }
        }

//        allowPaths.forEach((allowpath) -> {
//            if (url.contains(allowpath)) {
//                boolean b = false;
//                return b;
//            }
//        });

        return true;
    }

    @Override
    public Object run() throws ZuulException {

//        初始化运行上下文
        RequestContext context = RequestContext.getCurrentContext();

//        获取request
        HttpServletRequest request = context.getRequest();

//        看看request长啥样 哈哈哈哈
//        System.out.println();
//        System.out.println(request);
//        System.out.println();
//        System.out.println(request);
//        System.out.println();

//        获取token
        String token = CookieUtils.getCookieValue(request, this.jwtProperties.getCookieName());

        if (StringUtils.isBlank(token)) {
            context.setSendZuulResponse(false);
            context.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
        }

//        校验
        try {
//            进行校验
            JwtUtils.getInfoFromToken(token, this.jwtProperties.getPublicKey());
        } catch (Exception e) {
            e.printStackTrace();
            context.setSendZuulResponse(false);
            context.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
        }

        return null;
    }


}
