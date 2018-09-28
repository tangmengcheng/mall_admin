package com.mmall.util;

import java.math.BigDecimal;

/**
 * @program: mmall
 * @description: BigDecimal工具类
 * @author: Mr.Tang
 * @create: 2018-09-27 23:46
 **/
public class BigDecimalUtil {

    // 私有构造器使得该类不能再外面被实例化
    private BigDecimalUtil() {

    }

    public static BigDecimal add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));

        return b1.add(b2);
    }

    public static BigDecimal sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));

        return b1.subtract(b2);
    }

    public static BigDecimal mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));

        return b1.multiply(b2);
    }

    public static BigDecimal div(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));

        return b1.divide(b2, BigDecimal.ROUND_HALF_UP); // 四舍五入, 保留两位小数
    }
}
