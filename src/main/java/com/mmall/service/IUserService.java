package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;

/**
 * 用户信息接口
 */
public interface IUserService {

    // 用户登录
    ServerResponse<User> login(String username, String password);

    // 用户注册
    ServerResponse<String> register(User user);

    // 校验
    ServerResponse<String> checkValid(String str, String type);

    // 查询问题
    ServerResponse selectQuestion(String username);

    // 检查问题
    ServerResponse<String> checkAnswer(String username, String question, String answer);

    // 忘记密码重置密码
    ServerResponse<String> forgetRestPassword(String username, String newPassword, String forgetToken);

    // 登录状态下重置密码
    ServerResponse<String> resetPassword(String passwordOld, String passwordNew, User user);

    // 更新个人信息
    ServerResponse<User> updateInformation(User user);

    // 获取个人信息
    ServerResponse<User> getInformation(Integer userId);

    // 校验是否是管理员
    ServerResponse checkAdminRole(User user);
}
