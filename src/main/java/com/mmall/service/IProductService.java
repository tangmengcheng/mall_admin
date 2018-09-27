package com.mmall.service;

import com.github.pagehelper.PageInfo;
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

    // 返回商品列表
    ServerResponse<PageInfo> getProductList(int pageNum, int pageSize);

    // 返回搜索商品列表
    ServerResponse<PageInfo> searchProduct(String productName, Integer productId, int pageNum, int pageSize);

    // 前台获取产品详情
    ServerResponse<ProductDetailVO> getProductDetail(Integer productId);

    // 通过关键字和类目id进行搜索
    ServerResponse<PageInfo> getProductByKeywordCategory(String keyword, Integer categoryId, int pageNum, int pageSize, String orderBy);
}
