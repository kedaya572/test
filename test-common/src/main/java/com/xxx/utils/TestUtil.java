/**
 * 测试工具类
 *
 * @author shuimen
 */
package com.xxx.utils;

/**
 * 测试相关工具方法集合
 *
 * @author shuimen
 */
public class TestUtil {

    private TestUtil() {
    }

    /**
     * 打招呼
     *
     * @param name 姓名
     * @return 问候语
     */
    public static String sayHello(String name) {
        return "Hello, " + name + "!";
    }
}
