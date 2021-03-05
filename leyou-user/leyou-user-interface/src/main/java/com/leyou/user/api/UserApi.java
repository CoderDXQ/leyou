package com.leyou.user.api;

import com.leyou.user.pojo.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/18 5:01 下午
 */

//???这里是否应该有注解
//@GetMapping("query")
//@RequestMapping("query")
public interface UserApi {

//    @GetMapping("query")
//    User queryUser(@RequestParam("username") String username, @RequestParam("password") String password);

    /**
     * 用户验证
     *
     * @param username
     * @param password
     * @return
     */
    @GetMapping("query")
    User queryUser(@RequestParam("username") String username, @RequestParam("password") String password);
}
