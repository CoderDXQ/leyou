package com.leyou.item.bo;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/7 2:22 上午
 */

@Data
@Table(name = "tb_sku")
public class Sku {
    @Id//标记主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)//声明主键生成策略
    private Long id;
    private Long spuId;
    private String title;
    private String images;
    private Long price;
    private String ownSpec;// 商品特殊规格的键值对
    private String indexes;// 商品特殊规格的下标
    private Boolean enable;// 是否有效，逻辑删除用
    private Date createTime;// 创建时间
    private Date lastUpdateTime;// 最后修改时间
    @Transient
    private Integer stock;// 库存 }
}
