package com.mmall.common;

/**
 * @program: mmall
 * @description: 枚举类
 * @author: Mr.Tang
 * @create: 2018-09-14 19:04
 **/
public enum ResponseCode {

    SUCCESS(0, "SUCCESS"),

    ERROR(1, "ERROR"),

    NEED_LOGIN(10, "NEED_LOGIN"),

    ILLEGAL_ARGUMENTS(2, "ILLEGAL_ARGUMENTS");

    private final int code;

    private final String desc;

    ResponseCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}
