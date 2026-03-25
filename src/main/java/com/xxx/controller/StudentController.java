package com.xxx.controller;

import com.xxx.common.PageResult;
import com.xxx.common.Result;
import com.xxx.dto.StudentRequest;
import com.xxx.entity.Student;
import com.xxx.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @PostMapping
    public Result<?> add(@Valid @RequestBody StudentRequest req) {
        studentService.addStudent(req);
        return Result.success(null);
    }

    @PutMapping("/{id}")
    public Result<?> update(@PathVariable Long id, @Valid @RequestBody StudentRequest req) {
        studentService.updateStudent(id, req);
        return Result.success(null);
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return Result.success(null);
    }

    @GetMapping("/{id}")
    public Result<Student> get(@PathVariable Long id) {
        return Result.success(studentService.getStudent(id));
    }

    @GetMapping
    public Result<PageResult<Student>> list(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String major,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        return Result.success(studentService.listStudents(name, major, page, pageSize));
    }
}
