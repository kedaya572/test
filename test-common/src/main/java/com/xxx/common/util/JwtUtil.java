package com.xxx.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * JWT 工具类（无 Spring 依赖，供各微服务按需注册为 Bean）
 *
 * <p>注意：当前 logout 为无状态实现，依赖客户端丢弃 token。
 * 生产环境建议引入 Redis 实现 token 黑名单，支持集群场景。</p>
 */
public class JwtUtil {

    private final SecretKey key;
    private final long expiration;

    public JwtUtil(String secret, long expiration) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expiration = expiration;
    }

    /**
     * 生成 JWT token
     *
     * @param username 用户名
     * @return token 字符串
     */
    public String generate(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key)
                .compact();
    }

    /**
     * 从 token 中解析用户名
     *
     * @param token JWT token
     * @return 用户名
     */
    public String getUsername(String token) {
        return parse(token).getSubject();
    }

    /**
     * 校验 token 是否合法
     *
     * @param token JWT token
     * @return true 合法，false 非法或已过期
     */
    public boolean validate(String token) {
        try {
            parse(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Claims parse(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
