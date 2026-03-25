# 项目分析报告

## 项目概述

- **项目名称**: test
- **包名**: com.xxx
- **版本**: 1.0-SNAPSHOT
- **类型**: Spring Boot REST API 项目

## 技术栈

| 技术 | 版本 |
|------|------|
| Spring Boot | 3.2.3 |
| Java | 21 |
| MyBatis | 3.0.3 |
| MySQL | - |
| JWT (jjwt) | 0.12.5 |
| Lombok | - |
| Spring Security | - |

## 目录结构

```
src/main/java/com/xxx/
├── Application.java          # Spring Boot 启动类
├── Main.java                 # Main 入口
├── StudentManagementApplication.java  # 应用主类
│
├── config/                   # 配置类
│   ├── AppConfig.java
│   ├── SecurityConfig.java
│   └── WebConfig.java
│
├── common/                   # 通用类
│   ├── PageResult.java
│   └── Result.java
│
├── controller/               # 控制器
│   ├── AuthController.java
│   └── StudentController.java
│
├── dto/                      # 数据传输对象
│   ├── LoginRequest.java
│   ├── RegisterRequest.java
│   └── StudentRequest.java
│
├── entity/                   # 实体类
│   ├── Student.java
│   └── User.java
│
├── exception/                # 异常处理
│   └── GlobalExceptionHandler.java
│
├── interceptor/             # 拦截器
│   └── AuthInterceptor.java
│
├── mapper/                   # MyBatis Mapper
│   ├── StudentMapper.java
│   └── UserMapper.java
│
├── service/                 # 业务逻辑
│   ├── StudentService.java
│   └── UserService.java
│
└── util/                     # 工具类
    └── JwtUtil.java
```

## 主要功能模块

### 1. 认证模块 (Auth)
- 用户注册 `/api/auth/register`
- 用户登录 `/api/auth/login`
- JWT Token 验证

### 2. 学生管理模块 (Student)
- 学生列表查询
- 学生新增/编辑/删除

## API 接口

| 方法 | 路径 | 描述 |
|------|------|------|
| POST | /api/auth/register | 用户注册 |
| POST | /api/auth/login | 用户登录 |
| GET | /api/students | 获取学生列表 |
| POST | /api/students | 新增学生 |
| PUT | /api/students/{id} | 更新学生 |
| DELETE | /api/students/{id} | 删除学生 |

## 数据库表

- `user` - 用户表
- `student` - 学生表
