# 项目分析报告

## 项目概述

- **项目名称**: test
- **包名**: com.xxx
- **版本**: 1.0-SNAPSHOT
- **架构**: Spring Cloud 微服务

## 技术栈

| 技术 | 版本 | 说明 |
|------|------|------|
| Spring Boot | 3.2.3 | 基础框架 |
| Spring Cloud | 2023.0.1 | 微服务框架 |
| Spring Cloud Alibaba | 2023.0.1.0 | Nacos 服务注册发现 |
| Java | 21 | 运行环境 |
| MyBatis-Plus | 3.5.7 | ORM 框架，替代原 MyBatis |
| MySQL | 8.x | 数据库 |
| JWT (jjwt) | 0.12.5 | 身份认证 |
| Lombok | - | 代码简化 |
| Spring Security | - | 密码加密（BCrypt） |
| Nacos | 2.x | 服务注册与发现 |

## 模块结构

```
test/                          ← 父模块（packaging=pom）
├── test-common/               ← 公共模块（Result、PageResult、BusinessException、JwtUtil）
├── test-gateway/              ← 网关服务（端口 8080，JWT 鉴权、路由转发）
├── test-auth-service/         ← 认证服务（端口 8081，注册/登录/登出）
└── test-student-service/      ← 学生服务（端口 8082，学生 CRUD）
```

## 服务职责

| 服务 | 端口 | 职责 |
|------|------|------|
| test-gateway | 8080 | 统一入口，JWT 鉴权，路由转发 |
| test-auth-service | 8081 | 用户注册、登录、登出 |
| test-student-service | 8082 | 学生信息增删改查 |

## API 接口（前端调用地址不变，均通过网关 8080 访问）

| 方法 | 路径 | 服务 | 描述 | 是否需要认证 |
|------|------|------|------|------|
| POST | /api/auth/register | auth-service | 用户注册 | 否 |
| POST | /api/auth/login | auth-service | 用户登录 | 否 |
| POST | /api/auth/logout | auth-service | 用户登出 | 否 |
| GET | /api/students | student-service | 分页查询学生列表 | 是 |
| POST | /api/students | student-service | 新增学生 | 是 |
| GET | /api/students/{id} | student-service | 查询单个学生 | 是 |
| PUT | /api/students/{id} | student-service | 更新学生信息 | 是 |
| DELETE | /api/students/{id} | student-service | 删除学生 | 是 |

## 数据库表

| 表名 | 说明 |
|------|------|
| user | 用户表（含 is_deleted 逻辑删除字段） |
| student | 学生表（含 is_deleted 逻辑删除字段） |

## MyBatis-Plus 改造说明

1. **Mapper** 继承 `BaseMapper<T>`，无需编写 XML 文件
2. **Service** 接口继承 `IService<T>`，实现类继承 `ServiceImpl<M, T>`
3. **分页** 使用 `Page<T>` + `PaginationInnerInterceptor`，替代原手写 LIMIT/OFFSET
4. **条件查询** 使用 `LambdaQueryWrapper`，类型安全，避免字符串拼接
5. **逻辑删除** 通过 `@TableLogic` + `is_deleted` 字段自动实现，`deleteById` 执行 UPDATE 而非 DELETE
6. **自动填充** 通过 `MetaObjectHandler` 自动填充 `createdAt`、`updatedAt`

## 认证流程

```
前端 → 网关(8080) → JwtAuthFilter
                    ├── /api/auth/** → 直接转发至 test-auth-service
                    └── 其他路径 → 验证 JWT → 通过后注入 X-Username 头 → 转发至对应服务
```

## 启动顺序

1. 启动 **Nacos**（默认 localhost:8848）
2. 启动 **test-auth-service**（端口 8081）
3. 启动 **test-student-service**（端口 8082）
4. 启动 **test-gateway**（端口 8080）

## 后续扩展指南

新增微服务步骤：
1. 在根目录创建新模块目录，如 `test-xxx-service/`
2. 编写 `pom.xml`，继承父模块，引入所需依赖
3. 在父模块 `pom.xml` 的 `<modules>` 中注册新模块
4. 在 `test-gateway/src/main/resources/application.yml` 的 `routes` 下新增路由规则
5. 服务注册到 Nacos 后，网关自动路由，无需重启其他服务

## 待优化事项

- [ ] 引入 Redis 实现 JWT 黑名单（支持服务端真正登出及集群场景）
- [ ] 引入 Nacos Config 统一管理各服务配置（jwt.secret 等敏感配置）
- [ ] 网关层添加限流（Spring Cloud Gateway + Redis）
- [ ] 各服务添加单元测试
- [ ] 引入链路追踪（SkyWalking 或 Zipkin）
