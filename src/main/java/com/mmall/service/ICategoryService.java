package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.Category;

import java.util.List;

/**
 * @program: mmall
 * @description: 操作类目接口
 * @author: Mr.Tang
 * @create: 2018-09-20 21:39
 **/
public interface ICategoryService {

    // 添加品类
    ServerResponse addCategory(String categoryName, Integer parentId);

    // 更新品类名字
    ServerResponse updateCategoryName(Integer categoryId, String categoryName);

    // 获取品类的子品类
    ServerResponse<List<Category>> getChildParallelCategory(Integer categoryId);

    // 递归查询本节点的id及孩子节点的id
    ServerResponse selectCategoryAndChildrenById(Integer categoryId);
}
