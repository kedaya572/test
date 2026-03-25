package com.xxx.auth.controller;

import com.xxx.auth.dto.LoginRequest;
import com.xxx.auth.dto.RegisterRequest;
import com.xxx.auth.service.UserService;
import com.xxx.common.result.Result;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 认证接口
 *
 * <p>接口路径与原单体服务保持一致，前端无需修改。</p>
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public Result<?> register(@Valid @RequestBody RegisterRequest req) {
        userService.register(req);
        return Result.success(null);
    }

    /**
     * 用户登录
     *
     * @return 包含 token 的响应体
     */
    @PostMapping("/login")
    public Result<?> login(@Valid @RequestBody LoginRequest req) {
        String token = userService.login(req);
        return Result.success(Map.of("token", token));
    }

    /**
     * 用户登出
     *
     * <p>当前为无状态实现，依赖客户端丢弃 token。
     * 生产环境建议引入 Redis 黑名单实现真正的服务端登出。</p>
     */
    @PostMapping("/logout")
    public Result<?> logout() {
        return Result.success(null);
    }
}
