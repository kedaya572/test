package com.xxx.common.util;

/**
 * 字符串工具类
 */
public class StringUtil {

    /**
     * 判断字符串是否为空
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * 判断字符串是否非空
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 判断字符串是否为空串（仅检查 ""）
     */
    public static boolean isBlank(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * 去除字符串两端空格
     */
    public static String trim(String str) {
        return str == null ? null : str.trim();
    }

    /**
     * 字符串转大写
     */
    public static String toUpperCase(String str) {
        return str == null ? null : str.toUpperCase();
    }

    /**
     * 字符串转小写
     */
    public static String toLowerCase(String str) {
        return str == null ? null : str.toLowerCase();
    }

    /**
     * 判断字符串是否包含子串
     */
    public static boolean contains(String str, String searchStr) {
        return str != null && searchStr != null && str.contains(searchStr);
    }

    public static void main(String[] args) {
        System.out.print(isEmpty("1"));
    }
}