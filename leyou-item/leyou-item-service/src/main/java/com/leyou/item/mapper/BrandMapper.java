package com.leyou.item.mapper;

import com.leyou.item.pojo.Brand;
import jdk.internal.dynalink.linker.LinkerServices;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

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

    @Select("SELECT * FROM tb_brand a INNER JOIN tb_category_brand b on a.id=b.brand_id where b.category_id=#{cid}")
    List<Brand> selectBrandsByCid(Long cid);
}
