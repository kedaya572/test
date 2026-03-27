package com.xxx.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 时间工具类
 *
 * <p>提供常用的日期时间操作方法，包括获取当前时间、格式化、解析、日期加减及比较等。</p>
 *
 * @author shuimen
 */
public class DateUtil {

    /** 默认日期时间格式 */
    public static final String DEFAULT_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /** 默认日期格式 */
    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";

    private DateUtil() {
    }

    /**
     * 获取当前日期时间
     *
     * @return 当前 {@link LocalDateTime}
     */
    public static LocalDateTime now() {
        return LocalDateTime.now();
    }

    /**
     * 将 {@link LocalDateTime} 格式化为字符串
     *
     * @param dateTime 日期时间对象，不能为 null
     * @param pattern  格式模式，例如 {@code "yyyy-MM-dd HH:mm:ss"}
     * @return 格式化后的字符串
     */
    public static String format(LocalDateTime dateTime, String pattern) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 将 {@link LocalDateTime} 格式化为默认格式（{@code yyyy-MM-dd HH:mm:ss}）
     *
     * @param dateTime 日期时间对象，不能为 null
     * @return 格式化后的字符串
     */
    public static String format(LocalDateTime dateTime) {
        return format(dateTime, DEFAULT_DATETIME_PATTERN);
    }

    /**
     * 将字符串解析为 {@link LocalDateTime}
     *
     * @param text    日期时间字符串，不能为 null
     * @param pattern 格式模式，例如 {@code "yyyy-MM-dd HH:mm:ss"}
     * @return 解析后的 {@link LocalDateTime}
     * @throws java.time.format.DateTimeParseException 如果字符串与格式不匹配
     */
    public static LocalDateTime parse(String text, String pattern) {
        return LocalDateTime.parse(text, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 使用默认格式（{@code yyyy-MM-dd HH:mm:ss}）将字符串解析为 {@link LocalDateTime}
     *
     * @param text 日期时间字符串，不能为 null
     * @return 解析后的 {@link LocalDateTime}
     * @throws java.time.format.DateTimeParseException 如果字符串与默认格式不匹配
     */
    public static LocalDateTime parse(String text) {
        return parse(text, DEFAULT_DATETIME_PATTERN);
    }

    /**
     * 在指定日期上加减天数
     *
     * @param date 基准日期，不能为 null
     * @param days 要增加的天数，负数表示减少
     * @return 计算后的新 {@link LocalDate}
     */
    public static LocalDate addDays(LocalDate date, long days) {
        if (date == null) {
            return null;
        }
        return date.plusDays(days);
    }

    /**
     * 在指定日期时间上加减天数
     *
     * @param dateTime 基准日期时间，不能为 null
     * @param days     要增加的天数，负数表示减少
     * @return 计算后的新 {@link LocalDateTime}
     */
    public static LocalDateTime addDays(LocalDateTime dateTime, long days) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.plusDays(days);
    }

    /**
     * 判断日期 {@code a} 是否在日期 {@code b} 之前
     *
     * @param a 日期 a，不能为 null
     * @param b 日期 b，不能为 null
     * @return 若 a 早于 b 则返回 {@code true}，否则返回 {@code false}
     */
    public static boolean isBefore(LocalDateTime a, LocalDateTime b) {
        return a.isBefore(b);
    }

    /**
     * 判断日期 {@code a} 是否在日期 {@code b} 之后
     *
     * @param a 日期 a，不能为 null
     * @param b 日期 b，不能为 null
     * @return 若 a 晚于 b 则返回 {@code true}，否则返回 {@code false}
     */
    public static boolean isAfter(LocalDateTime a, LocalDateTime b) {
        return a.isAfter(b);
    }
}
