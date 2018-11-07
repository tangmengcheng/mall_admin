package com.mmall.dao;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    // 通过用户id和订单id查询订单
    Order selectByUserIdAndOrderNo(@Param("userId")Integer userId, @Param("orderNo") Long orderNo);

    // 通过订单号查询订单
    Order selectByOrderNo(Long orderNo);

    // 通过用户ID查询该用户订单列表
    List<Order> selectByUserId(Integer userId);

    // 获取订单列表
    List<Order> selectAllOrder();

}