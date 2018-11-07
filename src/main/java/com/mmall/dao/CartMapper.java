package com.mmall.dao;

import com.mmall.pojo.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);

    // 根据用户id和产品id查询购物车商品
    Cart selectCartByUserIdProductId(@Param(value = "userId") Integer userId, @Param(value = "productId") Integer productId);

    // 通过用户id,返回购物车集合
    List<Cart> selectCartByUserId(Integer userId);

    // 通过用户id查询购物车中商品选中的状态
    int selectCartProductCheckedStatusByUserId(Integer userId);

    // 通过用户id和产品多个id删除产品
    int deleteByUserIdProductIds(@Param("userId") Integer userId, @Param("productIdList") List<String> productIdList);

    // 全选或全反选商品 单独选货单独反选
    int checkedOrUnCheckedProduct(@Param("userId") Integer userId, @Param(value = "productId") Integer productId, @Param("checked") Integer checked);

    // 查询购物车中商品的数量
    // 注意：如果购物车中quantity数量为空时，基本类型是不能接受0的【可以换成Integer】
    int selectCartProductCount(@Param("userId") Integer userId);

    // 获取购物车中已经被勾选的产品
    List<Cart> selectCheckedCartByUserId(Integer userId);


}