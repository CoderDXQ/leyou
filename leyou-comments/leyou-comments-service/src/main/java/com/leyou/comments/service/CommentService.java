package com.leyou.comments.service;

import com.leyou.comments.bo.CommentRequestParam;
import com.leyou.comments.pojo.Review;
import sun.jvm.hotspot.debugger.Page;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/27 10:42 下午
 */
public interface CommentService {

    Review findOne(String id);

    boolean add(Long orderId, Review review);

    void update(Review review);

    void deleteById(String id);

    Page<Review> findReviewBySpuId(CommentRequestParam commentRequestParam);

    boolean updateThumbup(String id);

    boolean updateVisits(String id);

}
