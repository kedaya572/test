package com.xxx.controller;

import com.xxx.common.Result;
import com.xxx.dto.LoginRequest;
import com.xxx.dto.RegisterRequest;
import com.xxx.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/register")
    public Result<?> register(@Valid @RequestBody RegisterRequest req) {
        userService.register(req);
        return Result.success(null);
    }

    @PostMapping("/login")
    public Result<?> login(@Valid @RequestBody LoginRequest req) {
        String token = userService.login(req);
        return Result.success(Map.of("token", token));
    }

    @PostMapping("/logout")
    public Result<?> logout(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        userService.logout(authHeader);
        return Result.success(null);
    }
}