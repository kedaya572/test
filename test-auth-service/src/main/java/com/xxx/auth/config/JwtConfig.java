package com.xxx.auth.config;

import com.xxx.common.util.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * JWT Bean 配置
 */
@Configuration
public class JwtConfig {

    @Bean
    public JwtUtil jwtUtil(@Value("${jwt.secret}") String secret,
                           @Value("${jwt.expiration:86400000}") long expiration) {
        return new JwtUtil(secret, expiration);
    }
}
