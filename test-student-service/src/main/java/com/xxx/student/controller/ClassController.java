package com.xxx.student.controller;

import com.xxx.common.result.PageResult;
import com.xxx.common.result.Result;
import com.xxx.student.dto.ClassQueryRequest;
import com.xxx.student.dto.ClassRequest;
import com.xxx.student.entity.Clazz;
import com.xxx.student.service.ClassService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 班级管理接口
 *
 * @author shuimen
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/class")
@RequiredArgsConstructor
public class ClassController {

    private final ClassService classService;

    /**
     * 新增班级
     */
    @PostMapping
    public Result<?> add(@Valid @RequestBody ClassRequest req) {
        classService.addClass(req);
        return Result.success(null);
    }

    /**
     * 根据ID查询班级
     *
     * @param id 班级ID
     * @return 班级信息
     */
    @GetMapping("/{id}")
    public Result<Clazz> get(@PathVariable Long id) {
        return Result.success(classService.getClass(id));
    }

    /**
     * 分页查询班级列表
     *
     * <p>模糊匹配：className；精确匹配：grade、teacherId</p>
     */
    @GetMapping
    public Result<PageResult<Clazz>> list(ClassQueryRequest req) {
        log.info("分页查询班级列表：req={}", req);
        return Result.success(classService.pageClasses(req));
    }

    /**
     * 修改班级
     *
     * @param id  班级ID
     * @param req 修改内容
     * @return 操作结果
     */
    @PutMapping("/{id}")
    public Result<?> update(@PathVariable Long id, @Valid @RequestBody ClassRequest req) {
        classService.updateClass(id, req);
        return Result.success(null);
    }

    /**
     * 删除班级
     *
     * @param id 班级ID
     * @return 操作结果
     */
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        classService.deleteClass(id);
        return Result.success(null);
    }
}
