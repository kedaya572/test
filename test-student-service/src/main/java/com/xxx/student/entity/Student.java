package com.xxx.student.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 学生实体
 */
@Data
@TableName("student")
public class Student {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    private String gender;

    private Integer age;

    private String studentNo;

    private String major;

    private String email;

    private String phone;

    /** 是否启用：true-启用，false-禁用 */
    private Boolean enabled;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    /** 逻辑删除：0-未删除，1-已删除 */
    @TableLogic
    private Integer isDeleted;
}
