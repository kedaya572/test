package com.xxx.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5 工具类
 *
 * <p>提供 MD5 加密与校验功能。MD5 为不可逆哈希算法，适用于数据完整性校验，
 * 不建议用于密码存储（请使用 BCrypt 等加盐算法替代）。</p>
 *
 * @author shuimen
 */
public class MD5Util {

    private static final String ALGORITHM = "MD5";

    private MD5Util() {
    }

    /**
     * 对字符串进行 MD5 加密，返回 32 位小写十六进制字符串
     *
     * @param input 原始字符串，不能为 null
     * @return 32 位小写 MD5 摘要字符串
     * @throws IllegalArgumentException 如果 input 为 null
     * @throws IllegalStateException    如果当前 JVM 不支持 MD5 算法（通常不会发生）
     */
    public static String encrypt(String input) {
        if (input == null) {
            throw new IllegalArgumentException("input must not be null");
        }
        try {
            MessageDigest md = MessageDigest.getInstance(ALGORITHM);
            byte[] digest = md.digest(input.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("MD5 algorithm not available", e);
        }
    }

    /**
     * 校验原始字符串与 MD5 摘要是否匹配
     *
     * @param input  原始字符串，不能为 null
     * @param md5Hex 待比对的 32 位 MD5 十六进制字符串，不能为 null
     * @return 若匹配则返回 {@code true}，否则返回 {@code false}
     */
    public static boolean verify(String input, String md5Hex) {
        if (input == null || md5Hex == null) {
            return false;
        }
        return encrypt(input).equalsIgnoreCase(md5Hex);
    }

    /**
     * 将字节数组转换为小写十六进制字符串
     *
     * @param bytes 字节数组
     * @return 十六进制字符串
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
