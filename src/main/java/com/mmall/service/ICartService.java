package com.mmall.service;

import com.mmall.VO.CartVO;
import com.mmall.common.ServerResponse;

/**
 * @program: mmall
 * @description: 购物车的Service
 * @author: Mr.Tang
 * @create: 2018-09-27 23:02
 **/
public interface ICartService {

    // 查询购物车集合
    ServerResponse<CartVO> list(Integer userId);

    // 添加购物车
    ServerResponse<CartVO> add(Integer userId, Integer productId, Integer count);

    // 更新购物车
    ServerResponse<CartVO> update(Integer userId, Integer productId, Integer count);

    // 删除购物车中的产品
    ServerResponse<CartVO> deleteProduct(Integer userId, String productIds);

    // 全选或反全选商品 单独选货单独反选
    ServerResponse<CartVO> selectOrUnSelect(Integer userId, Integer productId, Integer checked);

    // 获取购物车中商品的数量
    ServerResponse<Integer> getCartProductCount(Integer userId);
}
