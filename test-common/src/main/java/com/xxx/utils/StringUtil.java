package com.xxx.utils;

/**
 * 字符串工具类
 *
 * @author shuimen
 */
public class StringUtil {

    private StringUtil() {}

    /**
     * 判断字符串是否为空（null 或空串）
     *
     * @param str 字符串
     * @return 是否为空
     */
    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
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
        return str.isBlank();
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

    /**
     * 驼峰转下划线
     *
     * <p>如：userName → user_name, XMLParser → xml_parser</p>
     *
     * @param str 驼峰格式字符串
     * @return 下划线格式字符串
     */
    public static String camelToUnderline(String str) {
        if (str == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (Character.isUpperCase(c)) {
                // 处理连续大写：如果是连续大写字母的最后一个，且后面还有小写字母，则需要转换
                if (i > 0 && !Character.isUpperCase(str.charAt(i - 1))) {
                    sb.append('_');
                } else if (i > 0 && i < str.length() - 1 
                           && Character.isUpperCase(str.charAt(i))
                           && Character.isLowerCase(str.charAt(i + 1))) {
                    // XMLParser 情况：P 后面是小写，需要在 P 之前加 _
                    sb.append('_');
                }
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 下划线转驼峰
     *
     * <p>如：user_name → userName, _user_name → _userName</p>
     *
     * @param str 下划线格式字符串
     * @return 驼峰格式字符串
     */
    public static String underlineToCamel(String str) {
        if (str == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        boolean upperNext = false;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '_') {
                upperNext = true;
            } else {
                if (upperNext) {
                    sb.append(Character.toUpperCase(c));
                    upperNext = false;
                } else {
                    sb.append(c);
                }
            }
        }
        return sb.toString();
    }

    /**
     * 安全转换为字符串
     *
     * <p>null 返回空字符串 ""，数组类型返回 Arrays.toString() 格式</p>
     *
     * @param obj 对象
     * @return 字符串
     */
    public static String toString(Object obj) {
        if (obj == null) {
            return "";
        }
        if (obj instanceof Object[]) {
            return java.util.Arrays.toString((Object[]) obj);
        }
        if (obj instanceof byte[]) {
            return java.util.Arrays.toString((byte[]) obj);
        }
        if (obj instanceof short[]) {
            return java.util.Arrays.toString((short[]) obj);
        }
        if (obj instanceof int[]) {
            return java.util.Arrays.toString((int[]) obj);
        }
        if (obj instanceof long[]) {
            return java.util.Arrays.toString((long[]) obj);
        }
        if (obj instanceof float[]) {
            return java.util.Arrays.toString((float[]) obj);
        }
        if (obj instanceof double[]) {
            return java.util.Arrays.toString((double[]) obj);
        }
        if (obj instanceof boolean[]) {
            return java.util.Arrays.toString((boolean[]) obj);
        }
        if (obj instanceof char[]) {
            return java.util.Arrays.toString((char[]) obj);
        }
        return obj.toString();
    }
}