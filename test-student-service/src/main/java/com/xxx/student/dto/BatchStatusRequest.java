package com.xxx.student.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * 批量更新学生状态请求 DTO
 */
@Data
public class BatchStatusRequest {

    /**
     * 学生 ID 列表
     */
    @NotEmpty(message = "ID列表不能为空")
    private List<Long> ids;

    /**
     * 目标状态：true-启用，false-禁用
     */
    @NotNull(message = "状态不能为空")
    private Boolean enabled;
}
