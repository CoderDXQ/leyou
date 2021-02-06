package com.leyou.item.pojo;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/7 12:40 上午
 */
@Data
@Table(name = "tb_spu_detail")
public class SpuDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long spuId;// 对应的SPU的id
    private String description;// 商品描述
    private String specialSpec;// 商品特殊规格的名称及可选值模板
    private String genericSpec;// 商品的全局规格属性
    private String packingList;// 包装清单
    private String afterService;// 售后服务
}
