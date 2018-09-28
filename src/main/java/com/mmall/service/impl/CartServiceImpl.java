package com.mmall.service.impl;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.mmall.VO.CartProductVO;
import com.mmall.VO.CartVO;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CartMapper;
import com.mmall.dao.ProductMapper;
import com.mmall.pojo.Cart;
import com.mmall.pojo.Product;
import com.mmall.service.ICartService;
import com.mmall.util.BigDecimalUtil;
import com.mmall.util.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * @program: mmall
 * @description: 购物车Service实现类
 * @author: Mr.Tang
 * @create: 2018-09-27 23:03
 **/
@Service("iCartService")
public class CartServiceImpl implements ICartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ProductMapper productMapper;


    public ServerResponse<CartVO> list(Integer userId) {
        CartVO cartVO = this.getCartVOLimit(userId);
        return ServerResponse.createBySuccess(cartVO);
    }

    public ServerResponse<CartVO> add(Integer userId, Integer productId, Integer count) {

        if (productId == null || count == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENTS.getCode(), ResponseCode.ILLEGAL_ARGUMENTS.getDesc());
        }

        Cart cart = cartMapper.selectCartByUserIdProductId(userId, productId);
        if (cart == null) {
            // 这个产品不在购物车中,需要新增这个产品的记录
            Cart cartItem = new Cart();
            cartItem.setQuantity(count);
            cartItem.setChecked(Const.Cart.CHECKED);
            cartItem.setProductId(productId);
            cartItem.setUserId(userId);

            cartMapper.insert(cartItem);
        } else {
            // 这个产品已经在购物车里
            // 如果产品已存在, 数量相加
            count = cart.getQuantity() + count;
            cart.setQuantity(count);

            cartMapper.updateByPrimaryKeySelective(cart);
        }

        return this.list(userId);

//        CartVO cartVO = this.getCartVOLimit(userId);
//
//        return ServerResponse.createBySuccess(cartVO);
    }

    public ServerResponse<CartVO> update(Integer userId, Integer productId, Integer count) {
        if (productId == null || count == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENTS.getCode(), ResponseCode.ILLEGAL_ARGUMENTS.getDesc());
        }

        Cart cart = cartMapper.selectCartByUserIdProductId(userId,productId);
        if (cart != null) {
            // 更新数量
            cart.setQuantity(count);
        }

        cartMapper.updateByPrimaryKeySelective(cart);

        return this.list(userId);

//        CartVO cartVO = this.getCartVOLimit(userId);
//
//        return ServerResponse.createBySuccess(cartVO);
    }

    public ServerResponse<CartVO> deleteProduct(Integer userId, String productIds) {
        List<String> productList = Splitter.on(",").splitToList(productIds);

        if (org.apache.commons.collections.CollectionUtils.isNotEmpty(productList)) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENTS.getCode(), ResponseCode.ILLEGAL_ARGUMENTS.getDesc());
        }

        cartMapper.deleteByUserIdProductIds(userId, productList);

        return this.list(userId);

//        CartVO cartVO = this.getCartVOLimit(userId);
//        return ServerResponse.createBySuccess(cartVO);

    }


    public ServerResponse<CartVO> selectOrUnSelect(Integer userId, Integer productId, Integer checked) {

        cartMapper.checkedOrUnCheckedProduct(userId, productId, checked);
        return this.list(userId);
    }

    public ServerResponse<Integer> getCartProductCount(Integer userId) {
        if (userId == null) {
            return ServerResponse.createBySuccess(0);
        }

        return ServerResponse.createBySuccess(cartMapper.selectCartProductCount(userId));
    }



    private CartVO getCartVOLimit(Integer userId) {
        CartVO cartVO = new CartVO();
        List<Cart> cartList = cartMapper.selectCartByUserId(userId);

        List<CartProductVO> cartProductVOList = Lists.newArrayList();

        BigDecimal cartTotalPrice = new BigDecimal("0");

        if (org.apache.commons.collections.CollectionUtils.isNotEmpty(cartList)) {
            for (Cart cartItem : cartList) {
                CartProductVO cartProductVO = new CartProductVO();

                cartProductVO.setId(cartItem.getId());
                cartProductVO.setUserId(userId);
                cartProductVO.setProductId(cartItem.getProductId());

                Product product = productMapper.selectByPrimaryKey(cartItem.getProductId());
                if (product != null) {
                    cartProductVO.setProductMainImage(product.getMainImage());
                    cartProductVO.setProductName(product.getName());
                    cartProductVO.setProductSubtitle(product.getSubtitle());
                    cartProductVO.setProductStatus(product.getStatus());
                    cartProductVO.setProductPrice(product.getPrice());
                    cartProductVO.setProductStock(product.getStock());

                    // 判断库存
                    int buyLimitCount = 0;
                    if (product.getStock() >= cartItem.getQuantity()) {
                        // 库存充足的时候
                        buyLimitCount = cartItem.getQuantity();
                        cartProductVO.setLimitQuantity(Const.Cart.LIMIT_NUM_SUCCESS);
                    } else {
                        buyLimitCount = product.getStock();
                        cartProductVO.setLimitQuantity(Const.Cart.LIMIT_NUM_FAIL);
                        // 购物车中更新有效库存
                        Cart cartForQuantity = new Cart();
                        cartForQuantity.setId(cartItem.getId());
                        cartForQuantity.setQuantity(buyLimitCount);

                        cartMapper.updateByPrimaryKeySelective(cartForQuantity);
                    }

                    cartProductVO.setQuantity(buyLimitCount);

                    // 计算总价
                    cartProductVO.setProductTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(), cartProductVO.getQuantity()));
                    cartProductVO.setProductChecked(cartItem.getChecked());
                }

                if (cartItem.getChecked() == Const.Cart.CHECKED) {
                    // 如果已经勾选, 增加到整个的购物车总价中
                    cartTotalPrice = BigDecimalUtil.add(cartTotalPrice.doubleValue(), cartProductVO.getProductTotalPrice().doubleValue());
                }

                cartProductVOList.add(cartProductVO);

            }
        }
        cartVO.setCartTotalPrice(cartTotalPrice);
        cartVO.setCartProductVOList(cartProductVOList);
        cartVO.setAllChecked(this.getAllCheckedStatus(userId));
        cartVO.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));

        return cartVO;
    }

    // 是否是全选状态
    private boolean getAllCheckedStatus(Integer userId) {
        if (userId == null) {
            return false;
        }
        return cartMapper.selectCartProductCheckedStatusByUserId(userId) == 0;
    }
}
