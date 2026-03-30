package com.xxx.student.controller;

import com.xxx.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试接口
 */
@Slf4j
@RestController
@RequestMapping("/api/test")
public class TestController {

    /**
     * GET 测试接口
     */
    @GetMapping("/hello")
    public Result<Map<String, Object>> hello() {
        Map<String, Object> data = new HashMap<>();
        data.put("message", "Hello, World!");
        data.put("timestamp", System.currentTimeMillis());
        log.info("测试接口 hello 被调用");
        return Result.success(data);
    }

    /**
     * POST 测试接口
     */
    @PostMapping("/echo")
    public Result<Map<String, Object>> echo(@RequestBody Map<String, Object> body) {
        log.info("测试接口 echo 被调用，body={}", body);
        Map<String, Object> result = new HashMap<>(body);
        result.put("received", true);
        result.put("timestamp", System.currentTimeMillis());
        return Result.success(result);
    }

    /**
     * 健康检查接口
     */
    @GetMapping("/health")
    public Result<Map<String, Object>> health() {
        Map<String, Object> data = new HashMap<>();
        data.put("status", "UP");
        data.put("timestamp", System.currentTimeMillis());
        data.put("service", "test-student-service");
        log.info("健康检查接口被调用");
        return Result.success(data);
    }
}