package com.leyou.comments.controller;

import com.leyou.comments.bo.CommentRequestParam;
import com.leyou.comments.pojo.Review;
import com.leyou.comments.service.CommentService;
import com.leyou.common.pojo.PageResult;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/27 10:31 下午
 */

@RequestMapping
@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private RedisTemplate redisTemplate;

    private final String THUMBUP_PREFIX = "thumbup";

    /**
     * 分页查询某一商品下的所有顶级评论
     */
    @GetMapping("list")
    public ResponseEntity findReviewBySpuId(@RequestBody CommentRequestParam requestParam) {
        Page<Review> result = (Page<Review>) commentService.findReviewBySpuId(requestParam);
        if (result == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        PageResult pageResult = new PageResult();
        pageResult.setTotal(result.getTotalElements());
        pageResult.setItems(result.getContent());
        pageResult.setTotalPage((long) result.getTotalPages());
        return ResponseEntity.ok(pageResult);
    }

    @PostMapping("comment/{orderId}")
    public ResponseEntity<Void> addReview(@PathVariable("orderId") Long orderId, @RequestBody Review review) {
        boolean result = this.commentService.add(orderId, review);
        if (!result) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/commentId/{id}")
    public ResponseEntity<Void> deletereview(@PathVariable("id") String id) {
        this.commentService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/comment")
    public ResponseEntity<Void> updateReview(@RequestBody Review review) {
        this.commentService.update(review);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/commentId/{id}")
    public ResponseEntity<Review> findReviewById(@PathVariable("id") String id) {
        Review review = this.commentService.findOne(id);
        if (review == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(review);
    }


}
