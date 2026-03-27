package com.xxx.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 数组工具类，提供常用的数组操作方法。
 *
 * @author shuimen
 */
public class ArrayUtil {

    private ArrayUtil() {
    }

    /**
     * 判断数组是否为空（null 或长度为 0）。
     *
     * @param array 待判断的数组
     * @param <T>   数组元素类型
     * @return 若数组为 null 或长度为 0 则返回 {@code true}，否则返回 {@code false}
     */
    public static <T> boolean isEmpty(T[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 判断数组是否不为空。
     *
     * @param array 待判断的数组
     * @param <T>   数组元素类型
     * @return 若数组不为 null 且长度大于 0 则返回 {@code true}，否则返回 {@code false}
     */
    public static <T> boolean isNotEmpty(T[] array) {
        return !isEmpty(array);
    }

    /**
     * 判断数组中是否包含指定元素。
     *
     * @param array   待搜索的数组
     * @param element 要查找的元素，允许为 {@code null}
     * @param <T>     数组元素类型
     * @return 若数组包含该元素则返回 {@code true}，否则返回 {@code false}
     */
    public static <T> boolean contains(T[] array, T element) {
        if (isEmpty(array)) {
            return false;
        }
        for (T item : array) {
            if (element == null ? item == null : element.equals(item)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 将数组元素以指定分隔符拼接为字符串。
     *
     * @param array     待拼接的数组
     * @param separator 分隔符
     * @param <T>       数组元素类型
     * @return 拼接后的字符串；若数组为空则返回空字符串
     */
    public static <T> String join(T[] array, String separator) {
        if (isEmpty(array)) {
            return "";
        }
        String sep = separator != null ? separator : "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            if (i > 0) {
                sb.append(sep);
            }
            sb.append(array[i]);
        }
        return sb.toString();
    }

    /**
     * 将数组转换为 {@link List}。
     *
     * @param array 待转换的数组
     * @param <T>   数组元素类型
     * @return 包含数组所有元素的可变 {@link List}；若数组为 null 则返回空列表
     */
    public static <T> List<T> toList(T[] array) {
        if (isEmpty(array)) {
            return new ArrayList<>();
        }
        return new ArrayList<>(Arrays.asList(array));
    }
}
