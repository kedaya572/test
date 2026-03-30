package com.xxx.student.util;

import lombok.extern.slf4j.Slf4j;

/**
 * 日志监控工具类
 * <p>
 * 用于监控日志输出是否正常，提供日志输出测试方法，
 * 可在系统初始化或健康检查时调用，验证日志框架运行状态。
 * </p>
 *
 * @author xxx
 * @since 1.0.0
 */
@Slf4j
public class LogMonitorUtil {

    private LogMonitorUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 监控日志输出是否正常
     *
     * @param message 待输出的日志消息
     */
    public static void monitorLog(String message) {
        log.info("LogMonitor: {}", message);
    }
}
