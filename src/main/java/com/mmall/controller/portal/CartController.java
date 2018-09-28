package com.mmall.controller.portal;

import com.mmall.VO.CartVO;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @program: mmall
 * @description: 购物车Controller
 * @author: Mr.Tang
 * @create: 2018-09-27 22:59
 **/
@RequestMapping("/cart/")
@Controller
public class CartController {

    @Autowired
    private ICartService iCartService;

    /**
     * 查询购物车集合
     *
     * @param session
     * @return
     */
    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse<CartVO> list(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENTS.getCode(), ResponseCode.ILLEGAL_ARGUMENTS.getDesc());
        }

        return iCartService.list(user.getId());
    }

    /**
     * 添加购物车
     *
     * @param session
     * @param count     数量
     * @param productId 商品id
     * @return
     */
    @RequestMapping("add.do")
    @ResponseBody
    public ServerResponse<CartVO> add(HttpSession session, Integer count, Integer productId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENTS.getCode(), ResponseCode.ILLEGAL_ARGUMENTS.getDesc());
        }

        return iCartService.add(user.getId(), productId, count);
    }

    /**
     * 更新购物车
     *
     * @param session
     * @param count     数量
     * @param productId 商品id
     * @return
     */
    @RequestMapping("update.do")
    @ResponseBody
    public ServerResponse<CartVO> update(HttpSession session, Integer count, Integer productId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENTS.getCode(), ResponseCode.ILLEGAL_ARGUMENTS.getDesc());
        }

        return iCartService.update(user.getId(), productId, count);
    }

    /**
     * 购物车中删除产品
     *
     * @param session
     * @param productIds 可能要删除多个产品
     * @return
     */
    @RequestMapping("delete_product.do")
    @ResponseBody
    public ServerResponse<CartVO> deleteProduct(HttpSession session, String productIds) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENTS.getCode(), ResponseCode.ILLEGAL_ARGUMENTS.getDesc());
        }

        return iCartService.deleteProduct(user.getId(), productIds);
    }

    // 全选
    @RequestMapping("select_all.do")
    @ResponseBody
    public ServerResponse<CartVO> selectAll(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENTS.getCode(), ResponseCode.ILLEGAL_ARGUMENTS.getDesc());
        }

        return iCartService.selectOrUnSelect(user.getId(), null, Const.Cart.CHECKED);
    }

    // 全反选
    @RequestMapping("un_select_all.do")
    @ResponseBody
    public ServerResponse<CartVO> unSelectAll(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENTS.getCode(), ResponseCode.ILLEGAL_ARGUMENTS.getDesc());
        }

        return iCartService.selectOrUnSelect(user.getId(), null, Const.Cart.UN_CHECKED);
    }

    // 单独选
    @RequestMapping("select.do")
    @ResponseBody
    public ServerResponse<CartVO> select(HttpSession session, Integer productId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENTS.getCode(), ResponseCode.ILLEGAL_ARGUMENTS.getDesc());
        }

        return iCartService.selectOrUnSelect(user.getId(), productId, Const.Cart.CHECKED);
    }

    // 单独反选
    @RequestMapping("un_select.do")
    @ResponseBody
    public ServerResponse<CartVO> unSelect(HttpSession session, Integer productId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENTS.getCode(), ResponseCode.ILLEGAL_ARGUMENTS.getDesc());
        }

        return iCartService.selectOrUnSelect(user.getId(), productId, Const.Cart.UN_CHECKED);
    }

    // 查询当前用户的购物车里面的产品数量, 如果一个产品有10个, 那么数量就是10
    @RequestMapping("get_cart_product_count.do")
    @ResponseBody
    public ServerResponse<Integer> getCartProductCount(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createBySuccess(0);
        }

        return iCartService.getCartProductCount(user.getId());
    }

}