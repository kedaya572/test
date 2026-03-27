package com.xxx.utils;

/**
 * 字符串工具类
 *
 * @author shuimen
 */
public class StringUtil {

    private StringUtil() {}

    /**
     * 判断字符串是否为空（null 或 length == 0）
     *
     * @param str 字符串
     * @return 是否为空
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * 判断字符串是否不为空
     *
     * @param str 字符串
     * @return 是否不为空
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 去除字符串首尾空白字符，若为 null 则返回 null
     *
     * @param str 字符串
     * @return 去除首尾空白后的字符串
     */
    public static String trim(String str) {
        return str == null ? null : str.trim();
    }

    /**
     * 判断字符串是否为空白（null、空串或仅含空白字符）
     *
     * @param str 字符串
     * @return 是否为空白
     */
    public static boolean isBlank(String str) {
        if (str == null) {
            return true;
        }
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串是否不为空白
     *
     * @param str 字符串
     * @return 是否不为空白
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }
}
