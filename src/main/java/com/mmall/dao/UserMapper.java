package com.mmall.dao;

import com.mmall.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    // 检查用户名存不存在
    int checkUsername(String username);

    // 检查email存不存在
    int checkEmail(String email);

    // 用户和密码登录
    User selectLogin(@Param("username") String username, @Param("password") String password);

    // 通过用户名来查询问题
    String selectQuestionByUsername(String username);

//    注意mybatis插件在传递多个参数的时候需要使用@Param注解
    int checkAnswer(@Param("username") String username, @Param("question") String question, @Param("answer") String answer);

    // 通过用户名更新密码
    int updatePasswordByUsername(@Param("username")String username, @Param("newPassword")String newPassword);

    // 重置密码
    int checkPassword(@Param("password") String password, @Param("userId") Integer userId);

    int checkEmailByUserId(@Param("email")String email, @Param("userId")Integer userId);

}