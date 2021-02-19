package com.leyou.cart.controller;

import com.leyou.cart.pojo.Cart;
import com.leyou.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/19 1:49 上午
 */

//@Controller
@RestController
@ResponseBody
public class CartController {

    @Autowired
    private CartService cartService;

    //    发送请求POST请求到 http://api.leyou.com/api/cart 配置Body中的参数  设置skuId为2600242
//    在此之前发送POST请求到 http://api.leyou.com/api/auth/accredit?username=liuyang&password=123456 以获得Cookie
    @PostMapping
    public ResponseEntity<Void> addCart(@RequestBody Cart cart) {
        this.cartService.addCart(cart);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<Cart>> queryCarts() {
        List<Cart> carts = this.cartService.queryCarts();
        if (CollectionUtils.isEmpty(carts)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(carts);
    }

    @PutMapping
    public ResponseEntity<Void> updateNum(@RequestBody Cart cart) {
        this.cartService.updateNum(cart);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{skuId}")
    public ResponseEntity<Void> deleteCart(@PathVariable("skuId") String skuId) {
        this.cartService.deleteCart(skuId);
        return ResponseEntity.ok().build();
    }


}
