package com.leyou.search.client;

import com.leyou.item.api.GoodsApi;
import com.leyou.item.pojo.SpuDetail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/10 12:44 上午
 */

//添加client接口调用商品微服务
@FeignClient(value = "item-service")
//@RequestMapping("/goods")
public interface GoodsClient extends GoodsApi {//另一个模块的开发人员在GoodsApi里声明接口之后，FeignClient的类里只需要继承就行

//    @GetMapping("spu/detail/{id}")
//    public SpuDetail querySpuDetailBySpuId(@PathVariable("id") Long id);
}
