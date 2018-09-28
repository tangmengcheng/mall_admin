package com.mmall.VO;

import java.math.BigDecimal;
import java.util.List;

/**
 * @program: mmall
 * @description: 购物车VO, 下面有CartProductVO的集合
 * @author: Mr.Tang
 * @create: 2018-09-27 23:22
 **/
public class CartVO {

    List<CartProductVO> cartProductVOList;

    private BigDecimal cartTotalPrice;

    private Boolean allChecked; // 是否已经都勾选

    private String imageHost;

    public List<CartProductVO> getCartProductVOList() {
        return cartProductVOList;
    }

    public void setCartProductVOList(List<CartProductVO> cartProductVOList) {
        this.cartProductVOList = cartProductVOList;
    }

    public BigDecimal getCartTotalPrice() {
        return cartTotalPrice;
    }

    public void setCartTotalPrice(BigDecimal cartTotalPrice) {
        this.cartTotalPrice = cartTotalPrice;
    }

    public Boolean getAllChecked() {
        return allChecked;
    }

    public void setAllChecked(Boolean allChecked) {
        this.allChecked = allChecked;
    }

    public String getImageHost() {
        return imageHost;
    }

    public void setImageHost(String imageHost) {
        this.imageHost = imageHost;
    }
}
