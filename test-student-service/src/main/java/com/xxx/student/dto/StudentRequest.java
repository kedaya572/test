package com.xxx.student.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 学生新增/更新请求 DTO
 */
@Data
public class StudentRequest {

    @NotBlank(message = "姓名不能为空")
    private String name;

    private String gender;

    private Integer age;

    @NotBlank(message = "学号不能为空")
    private String studentNo;

    private String major;

    private String email;

    private String phone;
}
