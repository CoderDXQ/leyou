package com.leyou.search.repository;

import com.leyou.search.pojo.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/10 3:13 上午
 */

//repository 存储的意思，类似于原来的DAO层
//ES相关操作
public interface GoodsRepository extends ElasticsearchRepository<Goods, Long> {
}
