package com.xxx.student.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 班级新增/更新请求 DTO
 *
 * @author shuimen
 */
@Data
public class ClassRequest {

    /** 班级名称 */
    @NotBlank(message = "班级名称不能为空")
    private String className;

    /** 年级 */
    @NotNull(message = "年级不能为空")
    private Integer grade;

    /** 班主任ID */
    private Long teacherId;
}
