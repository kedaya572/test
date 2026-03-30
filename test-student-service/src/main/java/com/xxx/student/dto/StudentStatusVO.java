package com.xxx.student.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 学生状态查询响应 VO
 */
@Data
public class StudentStatusVO {

    /** 学生 ID */
    private Long id;

    /** 姓名 */
    private String name;

    /** 学号 */
    private String studentNo;

    /** 是否启用：true-启用，false-禁用 */
    private Boolean enabled;

    /** 最近更新时间 */
    private LocalDateTime updatedAt;
}
