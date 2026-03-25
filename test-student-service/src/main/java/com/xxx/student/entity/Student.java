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

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    /** 逻辑删除：0-未删除，1-已删除 */
    @TableLogic
    private Integer isDeleted;
}
