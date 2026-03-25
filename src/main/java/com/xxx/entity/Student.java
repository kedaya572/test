package com.xxx.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Student {
    private Long id;
    private String name;
    private String gender;
    private Integer age;
    private String studentNo;
    private String major;
    private String email;
    private String phone;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}