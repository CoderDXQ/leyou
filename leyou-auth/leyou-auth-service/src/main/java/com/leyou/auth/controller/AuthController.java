package com.leyou.auth.controller;

import com.leyou.auth.config.JwtProperties;
import com.leyou.auth.service.AuthService;
import com.leyou.common.pojo.UserInfo;
import com.leyou.common.utils.CookieUtils;
import com.leyou.common.utils.JwtUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("verify")
    public ResponseEntity<UserInfo> verify(
            @CookieValue("LEYOU_TOKEN") String token,
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        try {
//          解析token  从token中获取信息
            UserInfo user = JwtUtils.getInfoFromToken(token, this.jwtProperties.getPublicKey());

            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

//            verify之后要重新设置过期时间
//            刷新jwt中的有效时间 本质上就是生成一个新的token 使用私钥生成一个token并设置过期时间
            token = JwtUtils.generateToken(user, this.jwtProperties.getPrivateKey(), this.jwtProperties.getExpire());

//            刷新cookie中的有效时间 本质上就是生成一个新的
            CookieUtils.setCookie(request, response, this.jwtProperties.getCookieName(), token, this.jwtProperties.getExpire() * 60);

//            解析成功返回用户信息
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //try体中的第一句异常时会执行这一句
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


}
