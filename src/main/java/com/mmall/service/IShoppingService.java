package com.mmall.service;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Shipping;

/**
 * @program: mmall
 * @description: 用户地址模块service
 * @author: Mr.Tang
 * @create: 2018-10-11 21:32
 **/
public interface IShoppingService {

    // 新建地址
    ServerResponse add(Integer userId, Shipping shipping);

    // 删除地址
    ServerResponse<String> del(Integer userId, Integer shippingId);

    // 更新地址
    ServerResponse update(Integer userId, Shipping shipping);

    // 查询地址
    ServerResponse<Shipping> select(Integer userId, Integer shippingId);

    // 返回地址集合
    ServerResponse<PageInfo> list(Integer userId, int pageNum, int pageSize);

}
