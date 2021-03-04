package com.leyou.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.pojo.PageResult;
import com.leyou.item.bo.SeckillParameter;
import com.leyou.item.bo.Sku;
import com.leyou.item.bo.SpuBo;
import com.leyou.item.mapper.*;
import com.leyou.item.pojo.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/7 12:57 上午
 */

@Service
public class GoodsService {

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private SpuDetailMapper spuDetailMapper;

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private SeckillMapper seckillMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static final String KEY_PREFIX = "leyou:seckill:stock";

    //    ？？？日志这一块可能有问题
//    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(GoodsService.class);


    //    saleable是否上架  分页查询的条件和规则是多样的 对应场景是商城的大的搜索页面（可以设置搜索条件和排序方式等） 然后分页进行展示
    public PageResult<SpuBo> querySpuByPage(String key, Boolean saleable, Integer page, Integer rows) {
        Example example = new Example(Spu.class);
//        创建标准
        Example.Criteria criteria = example.createCriteria();
        // 添加查询条件
        if (StringUtils.isNotBlank(key)) {
//            添加模糊查询条件
            criteria.andLike("title", "%" + key + "%");
        }

        //添加上下架的过滤条件
        if (saleable != null) {
            criteria.andEqualTo("saleable", saleable);
        }
        // 添加分页条件
        PageHelper.startPage(page, rows);
        // 执行查询,获取spu集合
        List<Spu> spus = this.spuMapper.selectByExample(example);
//        将List中的行转化为页面中的行
        PageInfo<Spu> pageInfo = new PageInfo<>(spus);

//        List<SpuBo> spuBos = new ArrayList<>();
//        spus.forEach(spu -> {
//            SpuBo spuBo = new SpuBo();
//            // copy共同属性的值到新的对象
//            BeanUtils.copyProperties(spu, spuBo);
//            // 查询分类名称
//            List<String> names = this.categoryService.queryNamesByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
//            spuBo.setCname(StringUtils.join(names, "/"));
//            // 查询品牌的名称
//            spuBo.setBname(this.brandMapper.selectByPrimaryKey(spu.getBrandId()).getName());
//            spuBos.add(spuBo);
//        });


        List<SpuBo> spuBos = spus.stream().map(
                spu -> {
                    SpuBo spuBo = new SpuBo();
                    BeanUtils.copyProperties(spu, spuBo);
//                    查询品牌名称
                    Brand brand = this.brandMapper.selectByPrimaryKey(spu.getBrandId());
                    spuBo.setBname(brand.getName());
//                    查询分类名称
                    List<String> names = this.categoryService.queryNamesByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
                    spuBo.setCname(StringUtils.join(names, "-"));

                    return spuBo;
                }
        ).collect(Collectors.toList());

//        返回pageResult<spuBo> 返回分页结果 总的页面的展示的行数和查询的结果
        return new PageResult<>(pageInfo.getTotal(), spuBos);
    }


    @Transactional //插入要加事务注解  隔离级别一般采用默认即可
    public void saveGoods(SpuBo spuBo) {
        // 新增spu
        // 设置默认字段
        spuBo.setId(null);
        spuBo.setSaleable(true);
        spuBo.setValid(true);
        spuBo.setCreateTime(new Date());
        spuBo.setLastUpdateTime(spuBo.getCreateTime());
        this.spuMapper.insertSelective(spuBo);
        // 新增spuDetail
        SpuDetail spuDetail = spuBo.getSpuDetail();
        spuDetail.setSpuId(spuBo.getId());
        this.spuDetailMapper.insertSelective(spuDetail);
//        更新库存
        saveSkuAndStock(spuBo);

        sendMsg("insert", spuBo.getId());
    }


    private void sendMsg(String type, Long id) {
        try {
            this.amqpTemplate.convertAndSend("item." + type, id);
        } catch (AmqpException e) {
            e.printStackTrace();
        }

    }

    private void saveSkuAndStock(SpuBo spuBo) {
        spuBo.getSkus().forEach(sku -> {
            // 新增sku
            sku.setSpuId(spuBo.getId());
            sku.setCreateTime(new Date());
            sku.setLastUpdateTime(sku.getCreateTime());
            this.skuMapper.insertSelective(sku);
            // 新增库存
            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            this.stockMapper.insertSelective(stock);
        });
    }

    public SpuDetail querySpuDetailBySpuId(Long spuId) {
        return this.spuDetailMapper.selectByPrimaryKey(spuId);
    }

    public List<Sku> querySkusBySpuId(Long spuId) {
        Sku record = new Sku();
        record.setSpuId(spuId);
        List<Sku> skus = this.skuMapper.select(record);

        skus.forEach(sku -> {
            Stock stock = this.stockMapper.selectByPrimaryKey(sku.getId());
            sku.setStock(stock.getStock());
        });

        return skus;
    }

    /**
     * 更新商品信息，更新的逻辑就是先删除后新增
     *
     * @param spuBo
     * @return
     */
    public void updateGoods(SpuBo spuBo) {

//        根据spuId查询要删除的sku
        Sku record = new Sku();
        record.setSpuId(spuBo.getId());
        List<Sku> skus = this.skuMapper.select(record);

        skus.forEach(sku -> {
//            删除stock(库存)
            this.stockMapper.deleteByPrimaryKey(sku.getId());
        });

//        删除sku
        Sku sku = new Sku();
        sku.setSpuId(spuBo.getId());
        this.skuMapper.delete(sku);

//        新增sku和stock
        this.saveSkuAndStock(spuBo);

//        更新spu和spuDetail
        spuBo.setCreateTime(null);
        spuBo.setLastUpdateTime(new Date());
        spuBo.setValid(null);
        spuBo.setSaleable(null);
        this.spuMapper.updateByPrimaryKeySelective(spuBo);
        this.spuDetailMapper.updateByPrimaryKeySelective(spuBo.getSpuDetail());

        sendMsg("update", spuBo.getId());
    }


    public Spu querySpuById(Long id) {
        return this.spuMapper.selectByPrimaryKey(id);
    }

    public Sku querySkuBySkuId(Long skuId) {
        System.out.println("进入Service层");
        System.out.println("skuId: " + skuId);
        Sku sku = this.skuMapper.selectByPrimaryKey(skuId);
        System.out.println("sku: " + sku.toString());
        return sku;
    }

    //    ???暂时没有在leyou-item-interface中的api中声明
    @Transactional(rollbackFor = Exception.class)
    public void addSeckillGoods(SeckillParameter seckillParameter) throws ParseException {

//        时间格式化
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//        查询商品
        Long id = seckillParameter.getId();
        Sku sku = this.querySkuBySkuId(id);
//        插入数据库
        SeckillGoods seckillGoods = new SeckillGoods();

        seckillGoods.setEnable(true);
        seckillGoods.setStartTime(sdf.parse(seckillParameter.getStartTime().trim()));
        seckillGoods.setEndTime(sdf.parse(seckillParameter.getEndTime().trim()));
        seckillGoods.setImage(sku.getImages());
        seckillGoods.setSkuId(sku.getId());
        seckillGoods.setStock(seckillParameter.getCount());
        seckillGoods.setTitle(sku.getTitle());
        seckillGoods.setSeckillPrice((double) (sku.getPrice() * seckillParameter.getCount()));
        this.seckillMapper.insert(seckillGoods);
//        更新库存 操作数据库
//        查询数据库中的库存
        Stock stock = stockMapper.selectByPrimaryKey(sku.getId());
        System.out.println(stock);
        if (stock != null) {
//          stock是数据库中的库存 seckillParameter是前端传过来的库存，即新增的库存  新的秒杀库存等于原来的秒杀库存加上新增的秒杀库存
            stock.setSeckillStock(stock.getSeckillStock() != null ? stock.getSeckillStock() + seckillParameter.getCount() : seckillParameter.getCount());
            stock.setSeckillTotal(stock.getSeckillStock() != null ? stock.getSeckillStock() + seckillParameter.getCount() : seckillParameter.getCount());
//            新的库存是原来的库存减去新增的秒杀库存 即秒杀库存都是从原来的库存中分出来的一部分
            stock.setStock(stock.getStock() - seckillParameter.getCount());
            this.stockMapper.updateByPrimaryKeySelective(stock);

        } else {
//            LOGGER.info("更新库存失败！");
            System.out.println("更新库存失败！");
        }

//        更新Redis中的秒杀库存
        updateSeckillStock();
    }

    private void updateSeckillStock() {
        List<SeckillGoods> seckillGoods = this.querySeckillGoods();
        if (seckillGoods == null || seckillGoods.size() == 0) {
            return;
        }
//        更新的逻辑是先删除后新增
        BoundHashOperations<String, Object, Object> hashOperations = this.stringRedisTemplate.boundHashOps(KEY_PREFIX);
        if (hashOperations.hasKey(KEY_PREFIX)) {
            hashOperations.delete(KEY_PREFIX);
        }
        seckillGoods.forEach(goods -> hashOperations.put(goods.getSkuId().toString(), goods.getStock().toString()));

    }

    //    查询秒杀商品
    public List<SeckillGoods> querySeckillGoods() {
//        设置查询条件
        Example example = new Example(SeckillGoods.class);
        example.createCriteria().andEqualTo("enable", true);
        List<SeckillGoods> list = this.seckillMapper.selectByExample(example);
        list.forEach(goods -> {
//            ？？？这里可能有数据处理错误 对照：https://blog.csdn.net/lyj2018gyq/article/details/83927764
            Stock stock = this.stockMapper.selectByPrimaryKey(goods.getSkuId());
//            秒杀商品的库存和秒杀库存是一样的
            goods.setStock(stock.getSeckillStock());
            goods.setSeckillTotal(stock.getSeckillStock());
        });
        return list;
    }

    


}
