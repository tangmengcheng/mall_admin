package com.mmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.mmall.VO.ProductDetailVO;
import com.mmall.VO.ProductListVO;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CategoryMapper;
import com.mmall.dao.ProductMapper;
import com.mmall.pojo.Category;
import com.mmall.pojo.Product;
import com.mmall.service.ICategoryService;
import com.mmall.service.IProductService;
import com.mmall.util.DateUtil;
import com.mmall.util.PropertiesUtil;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: mmall
 * @description: 产品业务逻辑实现类
 * @author: Mr.Tang
 * @create: 2018-09-21 15:32
 **/
@Service("iProductService")
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ICategoryService iCategoryService;

    public ServerResponse saveOrUpdate(Product product) {

        if (product != null) {

            if (StringUtils.isNoneBlank(product.getSubImages())) {
                String[] subImageArray = product.getSubImages().split(",");
                if (subImageArray.length > 0) {
                    // 将子图赋值给主图
                    product.setMainImage(subImageArray[0]);
                }
            }

            // 如果是更新,肯定有id
            if (product.getId() != null) {
                int rowCount = productMapper.updateByPrimaryKey(product);
                if (rowCount > 0) {
                    return ServerResponse.createBySuccessMessage("更新产品成功");
                }
                return ServerResponse.createBySuccessMessage("更新产品失败");
            } else {
                int rowCount = productMapper.insert(product);
                if (rowCount > 0) {
                    return ServerResponse.createBySuccessMessage("新增产品成功");
                }
                return ServerResponse.createBySuccessMessage("新增产品失败");
            }
        }

        return ServerResponse.createByErrorMessage("新增或更新产品信息参数不正确");
    }

    public ServerResponse<String> setSaleStatus(Integer productId, Integer status) {

        if (productId == null || status == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENTS.getCode(), ResponseCode.ILLEGAL_ARGUMENTS.getDesc());
        }

        Product product = new Product();
        product.setId(productId);
        product.setStatus(status);

        int rowCount = productMapper.updateByPrimaryKey(product);
        if (rowCount > 0) {
            return ServerResponse.createBySuccessMessage("修改产品状态成功");
        }
        return ServerResponse.createByErrorMessage("修改产品销售状态失败");

    }

    public ServerResponse<ProductDetailVO> manageProductDetail(Integer productId) {

        if (productId == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENTS.getCode(), ResponseCode.ILLEGAL_ARGUMENTS.getDesc());
        }

        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null) {
            return ServerResponse.createByErrorMessage("产品已下架或者删除");
        }

        // BO（Business Object）--> VO
        // VO（View Object）对象
        ProductDetailVO productDetailVO =assembleProductDetailVO(product);

        return ServerResponse.createBySuccess(productDetailVO);

    }

    private ProductDetailVO assembleProductDetailVO(Product product) {

        ProductDetailVO productDetailVO = new ProductDetailVO();
        productDetailVO.setId(product.getId());
        productDetailVO.setSubtitle(product.getSubtitle());
        productDetailVO.setPrice(product.getPrice());
        productDetailVO.setMainImage(product.getMainImage());
        productDetailVO.setSubImage(product.getSubImages());
        productDetailVO.setCategoryId(product.getCategoryId());
        productDetailVO.setDetail(product.getDetail());
        productDetailVO.setName(product.getName());
        productDetailVO.setStatus(product.getStatus());
        productDetailVO.setStock(product.getStock());

        productDetailVO.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","http://img.happymmall.com/"));
        // imageHost

        Category category = categoryMapper.selectByPrimaryKey(product.getCategoryId());
        if (category == null) {
            // 如果不存在,则被认为是根节点
            productDetailVO.setParentCategoryId(0);
        } else {
            productDetailVO.setParentCategoryId(category.getParentId());
        }
        // parentCategoryId


        // createTime
        productDetailVO.setCreateTime(DateUtil.dateToStr(product.getCreateTime()));
        // updateTime
        productDetailVO.setUpdateTime(DateUtil.dateToStr(product.getUpdateTime()));

        return productDetailVO;
    }

    public ServerResponse<PageInfo> getProductList(int pageNum, int pageSize) {
        // startPage ---- start
        // 填充自己的sql语句
        // PageHelper收尾

        PageHelper.startPage(pageNum, pageSize);
        List<Product> productList = productMapper.selectList();

        List<ProductListVO> productListVOList = Lists.newArrayList();

        for (Product productItem : productList) {
            ProductListVO productListVO = assembleProductListVO(productItem);
            productListVOList.add(productListVO);
        }

        PageInfo pageResult = new PageInfo(productList);

        pageResult.setList(productListVOList);

        return ServerResponse.createBySuccess(pageResult);

    }

    private ProductListVO assembleProductListVO(Product product) {

        ProductListVO productListVO = new ProductListVO();
        productListVO.setId(product.getId());
        productListVO.setCategoryId(product.getCategoryId());
        productListVO.setName(product.getName());
        productListVO.setMainImage(product.getMainImage());
        productListVO.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix", "http://img.happymmall.com/"));
        productListVO.setPrice(product.getPrice());
        productListVO.setSubtitle(product.getSubtitle());
        productListVO.setStatus(product.getStatus());

        return productListVO;

    }


    public ServerResponse<PageInfo> searchProduct(String productName, Integer productId, int pageNum, int pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        if (StringUtils.isNoneBlank(productName)) {
            productName = new StringBuilder().append("%").append(productName).append("%").toString();
        }

        List<Product> productList = productMapper.selectByNameAndProductId(productName, productId);

        List<ProductListVO> productListVOList = Lists.newArrayList();
        for (Product productItem : productList) {
            ProductListVO productListVO = assembleProductListVO(productItem);
            productListVOList.add(productListVO);
        }

        PageInfo pageResult = new PageInfo(productList);

        pageResult.setList(productListVOList);

        return ServerResponse.createBySuccess(pageResult);

    }

    public ServerResponse<ProductDetailVO> getProductDetail(Integer productId) {

        if (productId == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENTS.getCode(), ResponseCode.ILLEGAL_ARGUMENTS.getDesc());
        }

        Product product = productMapper.selectByPrimaryKey(productId);

        if (product == null) {
            return ServerResponse.createByErrorMessage("产品已下架或删除");
        }

        if (product.getStatus() != Const.ProductStatusEnum.ON_SALE.getCode()) {
            return ServerResponse.createByErrorMessage("产品已下架或者删除");
        }

        ProductDetailVO productDetailVO = assembleProductDetailVO(product);

        return ServerResponse.createBySuccess(productDetailVO);
    }

    public ServerResponse<PageInfo> getProductByKeywordCategory(String keyword, Integer categoryId, int pageNum, int pageSize, String orderBy) {

        if (StringUtils.isNoneBlank(keyword) && categoryId == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENTS.getCode(), ResponseCode.ILLEGAL_ARGUMENTS.getDesc());
        }

        List<Integer> categoryIdList = new ArrayList<Integer>();
        if (categoryId != null) {
            Category category = categoryMapper.selectByPrimaryKey(categoryId);
            if (category == null && StringUtils.isNoneBlank(keyword)) {
                // 没有该分类,并且没有关键字,这个时候返回一个空的集合,不报错,返回一个空的集合
                PageHelper.startPage(pageNum, pageSize);

                List<ProductListVO> productListVOList = Lists.newArrayList();

                PageInfo pageInfo = new PageInfo(productListVOList);

                return ServerResponse.createBySuccess(pageInfo);
            }
            categoryIdList = iCategoryService.selectCategoryAndChildrenById(category.getId()).getData();
        }
        if (StringUtils.isNoneBlank(keyword)) {
            keyword = new StringBuilder().append("%").append(keyword).append("%").toString();
        }

        PageHelper.startPage(pageNum, pageSize);
        // 排序处理
        if (StringUtils.isNoneBlank(orderBy)) {

            if (Const.ProductListOrderBy.PRICE_ASC_DESC.contains(orderBy)) {
                String[] orderByArray = orderBy.split("_");
                PageHelper.orderBy(orderByArray[0] + " " + orderByArray[1]);
            }
        }

        List<Product> productList = productMapper.selectByNameAndCategoryIds(StringUtils.isNoneBlank(keyword) ? null : keyword, categoryIdList.size() == 0 ? null : categoryIdList);

        List<ProductListVO> productListVOList = Lists.newArrayList();
        for (Product product : productList) {
            ProductListVO productListVO = assembleProductListVO(product);
            productListVOList.add(productListVO);
        }

        PageInfo pageInfo = new PageInfo(productList);
        pageInfo.setList(productListVOList);

        return ServerResponse.createBySuccess(pageInfo);

    }

}
