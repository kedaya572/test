package com.xxx.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 集合工具类
 *
 * @author shuimen
 */
public class CollectionUtil {

    private CollectionUtil() {}

    /**
     * 判断集合是否为空（null 或 size == 0）
     *
     * @param collection 集合
     * @return 是否为空
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * 判断集合是否不为空
     *
     * @param collection 集合
     * @return 是否不为空
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    /**
     * 如果元素不为 null，则添加到集合中
     *
     * @param collection 目标集合
     * @param element    待添加元素
     * @param <T>        元素类型
     */
    public static <T> void addIfNotNull(Collection<T> collection, T element) {
        if (collection != null && element != null) {
            collection.add(element);
        }
    }

    /**
     * 移除集合中所有 null 元素，返回新集合
     *
     * @param collection 原集合
     * @param <T>        元素类型
     * @return 不含 null 的新集合
     */
    public static <T> List<T> removeNulls(Collection<T> collection) {
        if (isEmpty(collection)) {
            return new ArrayList<>();
        }
        return collection.stream()
                .filter(e -> e != null)
                .collect(Collectors.toList());
    }

    /**
     * 按条件过滤集合，返回满足条件的元素列表
     *
     * @param collection 原集合
     * @param predicate  过滤条件
     * @param <T>        元素类型
     * @return 过滤后的列表
     */
    public static <T> List<T> filter(Collection<T> collection, Predicate<T> predicate) {
        if (isEmpty(collection) || predicate == null) {
            return new ArrayList<>();
        }
        return collection.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }
}
