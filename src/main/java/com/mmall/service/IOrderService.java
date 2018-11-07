package com.mmall.service;

import com.github.pagehelper.PageInfo;
import com.mmall.VO.OrderVO;
import com.mmall.common.ServerResponse;

import java.util.Map;

/**
 * @program: mmall
 * @description: 订单service
 * @author: Mr.Tang
 * @create: 2018-10-26 20:45
 **/
public interface IOrderService {

    // 支付service
    ServerResponse pay(Long orderNo, Integer userId, String path);

    // 支付宝的回调
    ServerResponse aliCallback(Map<String, String> params);

    // 查询订单支付状态
    ServerResponse queryOrderPayStatus(Integer userId, Long orderNo);

    // 创建订单
    ServerResponse createOrder(Integer userId, Integer shippingId);

    // 取消订单
    ServerResponse<String> cancel(Integer userId, Long orderNo);

    // 返回购物车中被勾选的商品
    ServerResponse getOrderCartProduct(Integer userId);

    // 获取订单详情
    ServerResponse<OrderVO> getOrderDetail(Integer userId, Long orderNo);

    // 获取个人中心订单列表
    ServerResponse<PageInfo> getOrderList(Integer userId, int pageNum, int pageSize);


    // 后台订单列表
    ServerResponse<PageInfo> manageList(int pageNum, int pageSize);

    // 后台订单详情
    ServerResponse<OrderVO> manageDetail(Long orderNo);

    // 后台商品搜索列表
    ServerResponse<PageInfo> manageSearch(Long orderNo, int pageNum, int pageSize);

    // 后台发货商品
    ServerResponse<String> manageSendGoods(Long orderNo);
}
