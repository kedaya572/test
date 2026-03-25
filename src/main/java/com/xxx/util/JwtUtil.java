package com.xxx.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class JwtUtil {

    private final SecretKey key;
    private final long expiration;
    // 内存黑名单：token -> 过期时间戳，应用重启后失效（单实例场景适用）
    private final Map<String, Long> blacklist = new ConcurrentHashMap<>();

    public JwtUtil(@Value("${jwt.secret}") String secret,
                   @Value("${jwt.expiration}") long expiration) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expiration = expiration;
    }

    public String generate(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key)
                .compact();
    }

    public String getUsername(String token) {
        return parse(token).getSubject();
    }

    public boolean validate(String token) {
        try {
            Long expiry = blacklist.get(token);
            if (expiry != null) {
                if (expiry > System.currentTimeMillis()) {
                    return false;
                }
                blacklist.remove(token); // 清理已过期的黑名单条目
            }
            parse(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void invalidate(String token) {
        try {
            Date exp = parse(token).getExpiration();
            blacklist.put(token, exp.getTime());
        } catch (Exception ignored) {
            // token 已无效，无需加入黑名单
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
