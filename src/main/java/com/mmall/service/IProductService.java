package com.mmall.service;

import com.mmall.VO.ProductDetailVO;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;

/**
 * @program: mmall
 * @description: 产品业务逻辑service
 * @author: Mr.Tang
 * @create: 2018-09-21 15:32
 **/
public interface IProductService {

    // 新增或更新产品
    ServerResponse saveOrUpdate(Product product);

    // 修改产品状态
    ServerResponse<String> setSaleStatus(Integer productId, Integer status);

    // 返回产品详情
    ServerResponse<ProductDetailVO> manageProductDetail(Integer productId);
}
