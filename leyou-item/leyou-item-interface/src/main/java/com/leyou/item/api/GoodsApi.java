package com.leyou.item.api;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.bo.Sku;
import com.leyou.item.bo.SpuBo;
import com.leyou.item.pojo.SeckillGoods;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/10 12:51 上午
 */
@RequestMapping("/goods")
public interface GoodsApi {

    @GetMapping("/spu/page")
    public PageResult<SpuBo> querySpuByPage(
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "saleable", required = false) Boolean saleable,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows);


    /**
     * 分页查询
     *
     * @param spuBo
     * @return
     */
    @PostMapping("goods")
    public Void saveGoods(@RequestBody SpuBo spuBo);

    @GetMapping("spu/detail/{id}")
    public SpuDetail querySpuDetailBySpuId(@PathVariable("id") Long id);

    @GetMapping("sku/list")
    public List<Sku> querySkusBySpuId(@RequestParam("id") Long spuId);

//
//    @PutMapping("goods")
//    public ResponseEntity<Void> updateGoods(@RequestBody SpuBo spuBo);

    @GetMapping("{id}")
    public Spu querySpuById(@PathVariable("id") Long id);

    @GetMapping("sku/{skuId}")
    public Sku querySkuBySkuId(@PathVariable("skuId") Long skuId);

    //???返回值可能有错误
    @GetMapping("/seckill/list")
    public ResponseEntity<List<SeckillGoods>> querySeckillGoods();


}
