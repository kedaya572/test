package com.xxx.student.controller;

import com.xxx.common.result.PageResult;
import com.xxx.common.result.Result;
import com.xxx.student.dto.BatchStatusRequest;
import com.xxx.student.dto.StudentRequest;
import com.xxx.student.entity.Student;
import com.xxx.student.service.StudentService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 学生管理接口
 *
 * <p>接口路径与原单体服务保持一致，前端无需修改。</p>
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    /**
     * 新增学生
     */
    @PostMapping
    public Result<?> add(@Valid @RequestBody StudentRequest req) {
        studentService.addStudent(req);
        return Result.success(null);
    }

    /**
     * 更新学生信息
     */
    @PutMapping("/{id}")
    public Result<?> update(@PathVariable Long id, @Valid @RequestBody StudentRequest req) {
        studentService.updateStudent(id, req);
        return Result.success(null);
    }

    /**
     * 删除学生
     */
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return Result.success(null);
    }

    /**
     * 查询单个学生
     */
    @GetMapping("/{id}")
    public Result<Student> get(@PathVariable Long id) {
        return Result.success(studentService.getStudent(id));
    }

    /**
     * 分页查询学生列表
     */
    @GetMapping
    public Result<PageResult<Student>> list(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String major,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        return Result.success(studentService.pageStudents(name, major, page, pageSize));
    }

    /**
     * 启用学生
     *
     * @param id 学生ID
     * @return 操作结果
     */
    @PutMapping("/{id}/enable")
    public Result<?> enable(@PathVariable Long id) {
        studentService.enableStudent(id);
        return Result.success(null);
    }

    /**
     * 禁用学生
     *
     * @param id 学生ID
     * @return 操作结果
     */
    @PutMapping("/{id}/disable")
    public Result<?> disable(@PathVariable Long id) {
        studentService.disableStudent(id);
        return Result.success(null);
    }

    /**
     * 根据学生id集合批量删除学生
     *
     * @param ids 学生ID集合
     * @return 操作结果
     */
    @DeleteMapping("/batch")
    public Result<?> batchDelete(@RequestBody List<Long> ids) {
        studentService.batchDeleteStudents(ids);
        return Result.success(null);
    }

    /**
     * 按姓名模糊查询学生列表
     *
     * @param name 姓名关键字，不能为空
     * @return 学生列表
     */
    @GetMapping("/search-by-name")
    public Result<List<Student>> searchByName(
            @RequestParam @NotNull(message = "姓名关键字不能为空") String name) {
        log.info("按姓名模糊查询：name={}", name);
        return Result.success(studentService.searchByName(name));
    }

    /**
     * 按年龄范围查询学生列表
     *
     * @param minAge 最小年龄（含）
     * @param maxAge 最大年龄（含）
     * @return 学生列表
     */
    @GetMapping("/by-age-range")
    public Result<List<Student>> getByAgeRange(
            @RequestParam @NotNull(message = "最小年龄不能为空") Integer minAge,
            @RequestParam @NotNull(message = "最大年龄不能为空") Integer maxAge) {
        log.info("按年龄范围查询：minAge={}, maxAge={}", minAge, maxAge);
        return Result.success(studentService.getByAgeRange(minAge, maxAge));
    }

    /**
     * 统计各专业学生人数
     *
     * @return key=专业名称，value=学生人数
     */
    @GetMapping("/count-by-major")
    public Result<Map<String, Long>> countByMajor() {
        log.info("统计专业学生数");
        return Result.success(studentService.countByMajor());
    }

    /**
     * 批量更新学生状态
     *
     * @param req 批量更新请求（含ID列表及目标状态）
     * @return 操作结果
     */
    @PutMapping("/batch-status")
    public Result<?> batchUpdateStatus(@Valid @RequestBody BatchStatusRequest req) {
        log.info("批量更新学生状态：ids={}, enabled={}", req.getIds(), req.getEnabled());
        studentService.batchUpdateStatus(req.getIds(), req.getEnabled());
        return Result.success(null);
    }

    /**
     * 导出学生数据到 Excel
     *
     * @param response HTTP 响应
     */
    @GetMapping("/export")
    public void exportStudents(HttpServletResponse response) {
        log.info("导出学生Excel");
        studentService.exportStudents(response);
    }
}