package com.xxx.utils;

/**
 * 字符串工具类
 */
public class StringUtils {

    /**
     * 判断字符串是否为空
     * @param str 要检查的字符串
     * @return 空返回 true，否则返回 false
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * 判断字符串是否非空
     * @param str 要检查的字符串
     * @return 非空返回 true，否则返回 false
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 截断字符串到指定长度
     * @param str 要截断的字符串
     * @param length 最大长度
     * @return 截断后的字符串，如果字符串长度小于等于指定长度则返回原字符串
     */
    public static String truncate(String str, int length) {
        if (str == null) {
            return null;
        }
        if (str.length() <= length) {
            return str;
        }
        return str.substring(0, length);
    }
}