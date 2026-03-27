package com.xxx.utils;

/**
 * 字符串工具类
 *
 * @author shuimen
 */
public class StringUtil {

    /**
     * 判断字符串是否为空
     * <p>字符串为 {@code null} 或长度为 0 时返回 {@code true}。</p>
     *
     * @param str 要检查的字符串
     * @return 若字符串为空则返回 {@code true}，否则返回 {@code false}
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * 判断字符串是否非空
     * <p>字符串不为 {@code null} 且长度大于 0 时返回 {@code true}。</p>
     *
     * @param str 要检查的字符串
     * @return 若字符串非空则返回 {@code true}，否则返回 {@code false}
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 去除字符串两端的空白字符
     * <p>若字符串为 {@code null}，则返回 {@code null}。</p>
     *
     * @param str 要处理的字符串
     * @return 去除两端空白后的字符串，若入参为 {@code null} 则返回 {@code null}
     */
    public static String trim(String str) {
        return str == null ? null : str.trim();
    }
}
