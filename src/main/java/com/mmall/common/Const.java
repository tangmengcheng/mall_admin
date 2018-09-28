package com.mmall.common;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * @program: mmall
 * @description: 常量类
 * @author: Mr.Tang
 * @create: 2018-09-14 19:51
 **/
public class Const {

    public static final String CURRENT_USER = "currentUser";

    public static final String EMAIL = "email";

    public static final String USERNAME = "username";

    public interface ProductListOrderBy {
        // Set的时间复杂度是O(1),List的时间复杂度是O(n)
        Set<String> PRICE_ASC_DESC = Sets.newHashSet("price_desc", "price_asc");
    }

    public interface Cart {
        int CHECKED = 1; // 既购物车选中状态
        int UN_CHECKED = 0; //既购物车中未选中状态

        String LIMIT_NUM_FAIL = "LIMIT_NUM_FAIL";
        String LIMIT_NUM_SUCCESS = "LIMIT_NUM_SUCCESS";
    }

    public interface Role {
        int ROLE_CUSTOMER = 0; // 普通用户
        int ROLE_ADMIN = 1; // 管理员
    }

    public enum ProductStatusEnum {
        ON_SALE(1, "在线");

        private String value;
        private int code;

        ProductStatusEnum(int code, String value) {
            this.value = value;
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }
    }
}
