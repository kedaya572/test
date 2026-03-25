package com.xxx.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxx.auth.dto.LoginRequest;
import com.xxx.auth.dto.RegisterRequest;
import com.xxx.auth.entity.User;
import com.xxx.auth.mapper.UserMapper;
import com.xxx.auth.service.UserService;
import com.xxx.common.exception.BusinessException;
import com.xxx.common.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public void register(RegisterRequest req) {
        boolean exists = lambdaQuery()
                .eq(User::getUsername, req.getUsername())
                .exists();
        if (exists) {
            throw new BusinessException("用户名已存在");
        }
        User user = new User();
        user.setUsername(req.getUsername());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setRole("USER");
        save(user);
    }

    @Override
    public String login(LoginRequest req) {
        User user = lambdaQuery()
                .eq(User::getUsername, req.getUsername())
                .one();
        if (user == null || !passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new BusinessException("用户名或密码错误");
        }
        return jwtUtil.generate(user.getUsername());
    }
}
