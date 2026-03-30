package com.xxx.student.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 班级实体
 *
 * <p>注意：Java 保留类名 Class 已被 {@link java.lang.Class} 占用，故实体命名为 Clazz，
 * 对应数据库表名 t_class。</p>
 *
 * @author shuimen
 */
@Data
@TableName("t_class")
public class Clazz {

    /** 主键，自增 */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 班级名称 */
    private String className;

    /** 年级 */
    private Integer grade;

    /** 班主任ID */
    private Long teacherId;

    /** 创建时间 */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /** 更新时间 */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /** 逻辑删除：0-未删除，1-已删除 */
    @TableLogic
    private Integer isDeleted;
}
