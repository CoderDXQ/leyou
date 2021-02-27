package com.leyou.comments.dao;

import com.leyou.comments.pojo.Review;
import com.leyou.comments.pojo.Spit;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.awt.print.Pageable;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/27 10:32 下午
 */
public interface SpitDao extends MongoRepository<Spit, String> {


}
