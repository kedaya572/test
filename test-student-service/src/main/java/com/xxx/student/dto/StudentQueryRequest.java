package com.xxx.student.dto;

import lombok.Data;

/**
 * 学生分页查询请求 DTO
 *
 * <p>模糊匹配字段：name、studentNo、major、email、phone（LIKE）</p>
 * <p>精确匹配字段：gender、age、enabled</p>
 */
@Data
public class StudentQueryRequest {

    /** 姓名（模糊匹配） */
    private String name;

    /** 学号（模糊匹配） */
    private String studentNo;

    /** 专业（模糊匹配） */
    private String major;

    /** 邮箱（模糊匹配） */
    private String email;

    /** 手机号（模糊匹配） */
    private String phone;

    /** 性别（精确匹配） */
    private String gender;

    /** 年龄（精确匹配） */
    private Integer age;

    /** 启用状态（精确匹配） */
    private Boolean enabled;

    /** 当前页码，从 1 开始，默认 1 */
    private int page = 1;

    /** 每页条数，默认 10 */
    private int pageSize = 10;
}
