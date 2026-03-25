package com.xxx.student.controller;

import com.xxx.common.result.PageResult;
import com.xxx.common.result.Result;
import com.xxx.student.dto.StudentRequest;
import com.xxx.student.entity.Student;
import com.xxx.student.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 学生管理接口
 *
 * <p>接口路径与原单体服务保持一致，前端无需修改。</p>
 */
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
}
