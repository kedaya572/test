-- 学生管理系统数据库
-- 遵循阿里巴巴 Java 开发手册：所有表必备三字段 id、create_time（此处为 created_at）、update_time（此处为 updated_at）
-- 新增 is_deleted 字段支持 MyBatis-Plus 逻辑删除

CREATE DATABASE IF NOT EXISTS student_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE student_db;

-- 用户表
CREATE TABLE IF NOT EXISTS `user` (
    `id`         BIGINT        NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
    `username`   VARCHAR(50)   NOT NULL UNIQUE COMMENT '用户名',
    `password`   VARCHAR(255)  NOT NULL COMMENT '密码（BCrypt 加密）',
    `role`       VARCHAR(20)   NOT NULL DEFAULT 'USER' COMMENT '角色：ADMIN/USER',
    `created_at` DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted` TINYINT(1)    NOT NULL DEFAULT 0 COMMENT '逻辑删除：0-未删除，1-已删除',
    PRIMARY KEY (`id`),
    INDEX `idx_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 学生表
CREATE TABLE IF NOT EXISTS `student` (
    `id`         BIGINT        NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
    `name`       VARCHAR(50)   NOT NULL COMMENT '姓名',
    `gender`     VARCHAR(10)   DEFAULT NULL COMMENT '性别',
    `age`        INT           DEFAULT NULL COMMENT '年龄',
    `student_no` VARCHAR(50)   NOT NULL UNIQUE COMMENT '学号',
    `major`      VARCHAR(50)   DEFAULT NULL COMMENT '专业',
    `email`      VARCHAR(100)  DEFAULT NULL COMMENT '邮箱',
    `phone`      VARCHAR(20)   DEFAULT NULL COMMENT '电话',
    `created_at` DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted` TINYINT(1)    NOT NULL DEFAULT 0 COMMENT '逻辑删除：0-未删除，1-已删除',
    PRIMARY KEY (`id`),
    INDEX `idx_student_no` (`student_no`),
    INDEX `idx_name` (`name`),
    INDEX `idx_major` (`major`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学生表';

-- 初始管理员用户（密码：admin123）
INSERT IGNORE INTO `user` (`username`, `password`, `role`) VALUES
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', 'ADMIN');
