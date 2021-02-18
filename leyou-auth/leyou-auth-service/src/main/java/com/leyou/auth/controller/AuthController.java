package com.leyou.auth.controller;

import com.leyou.auth.config.JwtProperties;
import com.leyou.auth.service.AuthService;
import com.leyou.common.utils.CookieUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/18 4:44 下午
 */

@Controller
@EnableConfigurationProperties(JwtProperties.class)//启动配置类
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired//不光要启动配置类 还要注入配置类
    private JwtProperties jwtProperties;

    //授权  使用postman发送post请求到 localhost:8087/accredit  Body里设置x-www-from-urlencoded的参数username为liuyang password为123456
    @PostMapping("accredit")
    public ResponseEntity<Void> accredit(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        String token = this.authService.accredit(username, password);

        if (StringUtils.isBlank(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

//        将token保存在cookie中
        CookieUtils.setCookie(request, response, this.jwtProperties.getCookieName(), token, this.jwtProperties.getExpire() * 60);

        return ResponseEntity.ok(null);
    }
}
