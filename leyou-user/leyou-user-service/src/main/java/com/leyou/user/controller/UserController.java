package com.leyou.user.controller;

import com.leyou.user.pojo.User;
import com.leyou.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/17 8:51 下午
 */

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    //使用postman发get请求到 localhost:8085/check/zhangsan/1
    @GetMapping("/check/{data}/{type}")
    public ResponseEntity<Boolean> checkUser(@PathVariable("data") String data, @PathVariable("type") Integer type) {
        Boolean bool = this.userService.checkUser(data, type);
        if (bool == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(bool);
    }

    //使用postman发post请求到 localhost:8085/code?phone=XXXXXXXXXXX 然后查看Redis中是否存在验证码
    @PostMapping("code")
    public ResponseEntity<Void> sendVerifyCode(@RequestParam("phone") String phone) {
        this.userService.sendVerifyCode(phone);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    //使用postman发post请求到 localhost:8085/register 并填写username,password,code,phone等 code需要调用sendVerifyCode接口获得，可以从Redis查看
    @PostMapping("register")
    public ResponseEntity<Void> register(User user, @RequestParam("code") String code) {
        this.userService.register(user, code);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
