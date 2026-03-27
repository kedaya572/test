package com.xxx.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 集合工具类，提供常用的集合操作方法。
 *
 * @author shuimen
 */
public final class CollectionUtil {

    private CollectionUtil() {
    }

    /**
     * 判断集合是否为空（null 或不含任何元素）。
     *
     * @param collection 待判断的集合
     * @return 集合为 null 或空时返回 {@code true}，否则返回 {@code false}
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * 判断集合是否不为空（非 null 且含有至少一个元素）。
     *
     * @param collection 待判断的集合
     * @return 集合非 null 且不为空时返回 {@code true}，否则返回 {@code false}
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    /**
     * 当元素不为 null 时，将其添加到集合中。
     *
     * @param collection 目标集合，不可为 null
     * @param element    待添加的元素
     * @param <T>        元素类型
     * @return 元素不为 null 且成功添加时返回 {@code true}，否则返回 {@code false}
     */
    public static <T> boolean addIfNotNull(Collection<T> collection, T element) {
        if (collection == null || element == null) {
            return false;
        }
        return collection.add(element);
    }

    /**
     * 移除集合中所有为 null 的元素，返回新列表，不修改原集合。
     *
     * @param collection 源集合
     * @param <T>        元素类型
     * @return 不含 null 元素的新列表；若 {@code collection} 为 null，则返回空列表
     */
    public static <T> List<T> removeNulls(Collection<T> collection) {
        if (collection == null) {
            return new ArrayList<>();
        }
        return collection.stream()
                .filter(e -> e != null)
                .collect(Collectors.toList());
    }

    /**
     * 按照指定条件过滤集合，返回满足条件的元素组成的新列表，不修改原集合。
     *
     * @param collection 源集合
     * @param predicate  过滤条件，不可为 null
     * @param <T>        元素类型
     * @return 满足条件的元素列表；若 {@code collection} 为 null，则返回空列表
     */
    public static <T> List<T> filter(Collection<T> collection, Predicate<T> predicate) {
        if (collection == null) {
            return new ArrayList<>();
        }
        return collection.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }
}
