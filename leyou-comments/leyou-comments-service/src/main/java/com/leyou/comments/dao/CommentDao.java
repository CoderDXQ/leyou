package com.leyou.comments.dao;

import com.leyou.comments.pojo.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/27 10:32 下午
 */
public interface CommentDao extends MongoRepository<Review, String> {
    /**
     * 分页查询
     */
    Page<Review> findReviewBySpuid(String spuId, Pageable pageable);
}
