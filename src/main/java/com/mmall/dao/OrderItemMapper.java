package com.mmall.dao;

import com.mmall.pojo.OrderItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderItem record);

    int insertSelective(OrderItem record);

    OrderItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderItem record);

    int updateByPrimaryKey(OrderItem record);

    // 通过订单id和用户id获取订单item集合
    List<OrderItem> getByOrderNoUserId(@Param("orderNo")Long orderNo, @Param("userId")Integer userId);

    // 通过订单号返回订单集合
    List<OrderItem> getByOrderNo(@Param("orderNo") Long orderNo);

    // 批量插入
    void batchInsert(@Param("orderItemList") List<OrderItem> orderItemList);
}