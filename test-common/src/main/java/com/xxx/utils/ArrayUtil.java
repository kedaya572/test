package com.xxx.utils;

import java.util.Arrays;
import java.util.List;

/**
 * 数组工具类
 *
 * @author shuimen
 */
public class ArrayUtil {

    private ArrayUtil() {}

    /**
     * 判断数组是否为空（null 或 length == 0）
     *
     * @param array 数组
     * @param <T>   元素类型
     * @return 是否为空
     */
    public static <T> boolean isEmpty(T[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 判断数组是否不为空
     *
     * @param array 数组
     * @param <T>   元素类型
     * @return 是否不为空
     */
    public static <T> boolean isNotEmpty(T[] array) {
        return !isEmpty(array);
    }

    /**
     * 判断数组中是否包含指定元素
     *
     * @param array   数组
     * @param element 目标元素
     * @param <T>     元素类型
     * @return 是否包含
     */
    public static <T> boolean contains(T[] array, T element) {
        if (isEmpty(array)) {
            return false;
        }
        for (T item : array) {
            if (item == element || (item != null && item.equals(element))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 将数组元素以指定分隔符拼接为字符串
     *
     * @param array     数组
     * @param separator 分隔符
     * @param <T>       元素类型
     * @return 拼接后的字符串
     */
    public static <T> String join(T[] array, String separator) {
        if (isEmpty(array)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        String sep = separator == null ? "" : separator;
        for (int i = 0; i < array.length; i++) {
            if (i > 0) {
                sb.append(sep);
            }
            sb.append(array[i]);
        }
        return sb.toString();
    }

    /**
     * 将数组转换为 List
     *
     * @param array 数组
     * @param <T>   元素类型
     * @return 对应的 List，数组为空时返回空 List
     */
    public static <T> List<T> toList(T[] array) {
        if (isEmpty(array)) {
            return Arrays.asList();
        }
        return Arrays.asList(array);
    }
}
