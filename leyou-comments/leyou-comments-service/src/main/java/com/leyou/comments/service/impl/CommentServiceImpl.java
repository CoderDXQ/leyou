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
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Date;
import java.util.List;


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
    @Transactional(rollbackFor = Exception.class)
    public boolean add(Long orderId, Review review) {

//        查询用户是否在该商品下发表过顶级评论 默认只能发表一次
        if (review.getIsparent()) {
            Query query2 = new Query();
            query2.addCriteria(Criteria.where("userid").is(review.getUserid()));
            query2.addCriteria(Criteria.where("orderid").is(review.getOrderid()));
            query2.addCriteria(Criteria.where("spuid").is(review.getSpuid()));
            List<Review> old = this.mongoTemplate.find(query2, Review.class);
            if (old.size() > 0 && old.get(0).getIsparent()) {
                return false;
            }
        }

//        修改订单状态 6代表评价状态
        boolean result = this.orderClient.updateOrderStatus(orderId, 6).getBody();
        if (!result) {
            return false;
        }

//        添加评论 设置主键
        review.set_id(idWorker.nextId() + "");
        review.setPublishtime(new Date());
        review.setComment(0);
        review.setThumbup(0);
        review.setVisits(0);
        if (review.getParentid() != null && !"".equals(review.getParentid())) {
//            如果存在上级id，则上级评论数+1，将上级评论的isParent设置为true，浏览量+1
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(review.getParentid()));
            Update update = new Update();
            update.inc("commit", 1);
            update.set("isParent", true);
            update.inc("visits", 1);
            this.mongoTemplate.updateFirst(query, update, "review");
        }
//        数据库存储一份
        commentDao.save(review);
        return true;
    }

    @Override
    public void update(Review review) {

    }

    @Override
    public void deleteById(String id) {
        commentDao.deleteById(id);
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
