package com.xxx.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxx.auth.dto.LoginRequest;
import com.xxx.auth.dto.RegisterRequest;
import com.xxx.auth.entity.User;

/**
 * 用户服务接口
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param req 注册请求
     */
    void register(RegisterRequest req);

    /**
     * 用户登录
     *
     * @param req 登录请求
     * @return JWT token
     */
    String login(LoginRequest req);
}
