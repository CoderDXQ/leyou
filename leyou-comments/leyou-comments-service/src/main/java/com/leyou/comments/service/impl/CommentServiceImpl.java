package com.leyou.comments.service.impl;

import com.leyou.comments.bo.CommentRequestParam;
import com.leyou.comments.client.OrderClient;
import com.leyou.comments.dao.CommentDao;
import com.leyou.comments.pojo.Review;
import com.leyou.comments.service.CommentService;
import com.leyou.common.pojo.PageResult;
import com.leyou.common.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/27 10:42 下午
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private OrderClient orderClient;


    @Override
    public Review findOne(String id) {
        return null;
    }

    @Override
    public boolean add(Long orderId, Review review) {
        return false;
    }

    @Override
    public void update(Review review) {

    }

    @Override
    public void deleteById(String id) {

    }

    @Override
    public Page<Review> findReviewBySpuId(CommentRequestParam commentRequestParam) {
        PageRequest pageRequest = PageRequest.of(commentRequestParam.getPage() - 1, commentRequestParam.getDefaultSize());
        return this.commentDao.findReviewBySpuid(commentRequestParam.getSpuId() + "", pageRequest);
    }

    @Override
    public boolean updateThumbup(String id) {
        return false;
    }

    @Override
    public boolean updateVisits(String id) {
        return false;
    }
}
