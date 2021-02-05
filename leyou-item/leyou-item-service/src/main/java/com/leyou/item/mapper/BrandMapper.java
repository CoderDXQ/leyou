package com.leyou.item.mapper;

import com.leyou.item.pojo.Brand;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/6 1:39 上午
 */
public interface BrandMapper extends Mapper<Brand> {

//    @Insert("INSERT INTO tb_category_brand (category_id, brand_id) VALUES (#{cid},#{bid}")
//    void insertCategoryAndBrand(@Param("cid") Long cid, @Param("bid") Long bid);

    @Insert("INSERT INTO tb_category_brand (category_id, brand_id) VALUES (#{cid},#{bid})")
    int insertCategoryAndBrand(@Param("cid") Long cid, @Param("bid") Long bid);
}
