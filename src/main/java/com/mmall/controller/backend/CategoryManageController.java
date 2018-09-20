package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.ICategoryService;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @program: mmall
 * @description: 后台类目管理
 * @author: Mr.Tang
 * @create: 2018-09-20 21:26
 **/
@Controller
@RequestMapping("/manage/category")
public class CategoryManageController {

    @Autowired
    private IUserService iUserService;
    @Autowired
    private ICategoryService iCategoryService;

    /**
     * 添加品类
     * @param session
     * @param categoryName 品类名称
     * @param parentId 品类父id
     * @return
     */
    @RequestMapping("add_category.do")
    @ResponseBody
    public ServerResponse addCategory(HttpSession session, String categoryName,@RequestParam(value = "parentId", defaultValue = "0") int parentId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录");
        }

        // 检验一下，是否是管理员
        if (iUserService.checkAdminRole(user).isSuccess()) {
            // 是管理员
            // 增加我们处理分类的逻辑

            return iCategoryService.addCategory(categoryName, parentId);

        } else {
            return ServerResponse.createByErrorMessage("无权操作,需要管理员权限");
        }
    }

    /**
     * 更新品类名称
     * @param session
     * @param categoryId 品类id
     * @param categoryName 品类名称
     * @return
     */
    @RequestMapping("set_category_name.do")
    @ResponseBody
    public ServerResponse setCategoryName(HttpSession session, Integer categoryId, String categoryName) {

        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录");
        }

        // 检验一下，是否是管理员
        if (iUserService.checkAdminRole(user).isSuccess()) {
            // 更新category

            return iCategoryService.updateCategoryName(categoryId, categoryName);

        } else {
            return ServerResponse.createByErrorMessage("无权操作,需要管理员权限");
        }
    }

    /**
     * 获取平级的子品类
     * @param session
     * @param categoryId 品类id
     * @return
     */
    @RequestMapping("get_category.do")
    @ResponseBody
    public ServerResponse getChildParallelCategory(HttpSession session,@RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {

        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录");
        }

        // 检验一下，是否是管理员
        if (iUserService.checkAdminRole(user).isSuccess()) {

            // 查询子节点的category信息,并且不递归,保持平级
            return iCategoryService.getChildParallelCategory(categoryId);
        } else {
            return ServerResponse.createByErrorMessage("无权操作,需要管理员权限");
        }
    }

    @RequestMapping("get_deep_category.do")
    @ResponseBody
    public ServerResponse getCategoryAndDeepChildrenCategory(HttpSession session, @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录");
        }

        // 检验一下，是否是管理员
        if (iUserService.checkAdminRole(user).isSuccess()) {

            // 查询当前节点的id和递归子节点的id
            // 0->10000->1000000
            return iCategoryService.selectCategoryAndChildrenById(categoryId);

        } else {
            return ServerResponse.createByErrorMessage("无权操作,需要管理员权限");
        }
    }

}
