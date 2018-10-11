package com.mmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.mmall.common.ServerResponse;
import com.mmall.dao.ShippingMapper;
import com.mmall.pojo.Shipping;
import com.mmall.service.IShoppingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @program: mmall
 * @description: 用户地址模块ServiceImpl实现类
 * @author: Mr.Tang
 * @create: 2018-10-11 21:33
 **/
@Service("iShoppingService")
public class ShoppingServiceImpl implements IShoppingService {

    @Autowired
    private ShippingMapper shippingMapper;

    public ServerResponse add(Integer userId, Shipping shipping) {

        shipping.setUserId(userId);
        int rowCount = shippingMapper.insert(shipping);

        if (rowCount > 0) {
            Map result = Maps.newHashMap();
            result.put("shippingId", shipping.getId());
            return ServerResponse.createBySuccess("新建地址成功", result);
        }
        return ServerResponse.createByErrorMessage("新建地址失败");
    }

    public ServerResponse<String> del(Integer userId, Integer shippingId) {

        int resultCount = shippingMapper.deleteByShippingIdUserId(userId, shippingId);
        if (resultCount > 0) {
            return ServerResponse.createBySuccessMessage("删除地址成功");
        }
        return ServerResponse.createByErrorMessage("删除地址失败");
    }

    public ServerResponse update(Integer userId, Shipping shipping) {

        shipping.setUserId(userId);
        int rowCount = shippingMapper.updateShipping(shipping);
        if (rowCount > 0) {
            return ServerResponse.createBySuccessMessage("更新地址成功");
        }
        return ServerResponse.createByErrorMessage("更新地址失败");
    }

    public ServerResponse<Shipping> select(Integer userId, Integer shippingId) {

        Shipping shipping = shippingMapper.selectByShippingIdUserId(userId, shippingId);
        if (shipping == null) {
            return ServerResponse.createByErrorMessage("无法查询到改地址");
        }
        return ServerResponse.createBySuccess("查询地址成功", shipping);
    }

    public ServerResponse<PageInfo> list(Integer userId, int pageNum, int pageSize) {

        PageHelper.startPage(pageNum, pageSize);

        List<Shipping> shippingList = shippingMapper.selectByShippingId(userId);

        PageInfo pageInfo = new PageInfo(shippingList);

        return ServerResponse.createBySuccess(pageInfo);
    }

}
