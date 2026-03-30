-- 班级表建表语句
-- 数据库：student_db
-- 作者：shuimen

CREATE TABLE IF NOT EXISTS `t_class` (
    `id`          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `class_name`  VARCHAR(64)  NOT NULL                COMMENT '班级名称',
    `grade`       INT          NOT NULL                COMMENT '年级',
    `teacher_id`  BIGINT                               COMMENT '班主任ID',
    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted`  TINYINT      NOT NULL DEFAULT 0       COMMENT '逻辑删除：0-未删除，1-已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_grade_class_name` (`grade`, `class_name`, `is_deleted`) COMMENT '同年级班级名称唯一（逻辑删除兜底）'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '班级表';

-- 初始化数据
INSERT INTO `t_class` (`class_name`, `grade`, `teacher_id`)
VALUES ('一班', 1, NULL),
       ('二班', 1, NULL),
       ('一班', 2, NULL),
       ('二班', 2, NULL);
