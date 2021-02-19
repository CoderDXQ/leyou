package com.leyou.order.mapper;

import com.leyou.order.pojo.Order;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface OrderMapper extends Mapper<Order> {

    //    具体实现在OrderMapper.xml中
    List<Order> queryOrderList(
            @Param("userId") Long userId,
            @Param("status") Integer status);
}
