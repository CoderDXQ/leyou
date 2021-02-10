package com.leyou.elasticsearch.test;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.bo.SpuBo;
import com.leyou.search.client.GoodsClient;
import com.leyou.search.pojo.Goods;
import com.leyou.search.repository.GoodsRepository;
import com.leyou.search.service.SearchService;
import com.netflix.discovery.converters.Auto;
import net.bytebuddy.asm.Advice;
import org.elasticsearch.search.aggregations.metrics.avg.InternalAvg;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/10 3:11 上午
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class ElasticsearchTest {

    @Autowired
    private ElasticsearchTemplate template;

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private SearchService searchService;

    @Autowired
    private GoodsClient goodsClient;

    @Test
    public void test() {

//        创建索引和映射
        this.template.createIndex(Goods.class);
        this.template.putMapping(Goods.class);

        Integer page = 1;//当前页码
        Integer rows_stable = 100;
        Integer rows = rows_stable;//每页最多的行数

        do {
//        从数据库中一页一页的导出数据然后存到ES中

//        分页查询数据  分页查询的条件和规则是多样的
            PageResult<SpuBo> result = this.goodsClient.querySpuByPage(null, true, page, rows);
//        获取当前页的数据
            List<SpuBo> items = result.getItems();

//        处理数据
            List<Goods> goodsList = items.stream().map(spuBo -> {
                try {
                    return this.searchService.buildGoods(spuBo);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }).collect(Collectors.toList());

//        执行ES新增数据的方法
            this.goodsRepository.saveAll(goodsList);

            rows = items.size();
            page++;

        } while (rows == rows_stable);


    }

}
