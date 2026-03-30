package com.xxx.student.dto;

import lombok.Data;

/**
 * 班级分页查询请求 DTO
 *
 * <p>模糊匹配字段：className（LIKE）；精确匹配字段：grade、teacherId</p>
 *
 * @author shuimen
 */
@Data
public class ClassQueryRequest {

    /** 班级名称（模糊匹配） */
    private String className;

    /** 年级（精确匹配） */
    private Integer grade;

    /** 班主任ID（精确匹配） */
    private Long teacherId;

    /** 当前页码，从 1 开始，默认 1 */
    private int page = 1;

    /** 每页条数，默认 10 */
    private int pageSize = 10;
}
