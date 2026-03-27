package com.xxx.utils;

/**
 * 数学工具类，提供基本的四则运算方法。
 *
 * @author shuimen
 */
public class MathUtil {

    private MathUtil() {}

    /**
     * 加法：返回两个数之和。
     *
     * @param a 被加数
     * @param b 加数
     * @return a + b
     */
    public static double add(double a, double b) {
        return a + b;
    }

    /**
     * 减法：返回两个数之差。
     *
     * @param a 被减数
     * @param b 减数
     * @return a - b
     */
    public static double sub(double a, double b) {
        return a - b;
    }

    /**
     * 乘法：返回两个数之积。
     *
     * @param a 被乘数
     * @param b 乘数
     * @return a * b
     */
    public static double mul(double a, double b) {
        return a * b;
    }

    /**
     * 除法：返回两个数之商。
     *
     * @param a 被除数
     * @param b 除数（不能为 0）
     * @return a / b
     * @throws ArithmeticException 当除数为 0 时抛出
     */
    public static double div(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("除数不能为 0");
        }
        return a / b;
    }
}
