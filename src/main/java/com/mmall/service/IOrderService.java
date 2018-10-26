package com.mmall.service;

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
}
