package com.xxx.student.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxx.common.exception.BusinessException;
import com.xxx.common.result.PageResult;
import com.xxx.student.dto.StudentRequest;
import com.xxx.student.entity.Student;
import com.xxx.student.mapper.StudentMapper;
import com.xxx.student.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * 学生服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    /** 性别枚举 */
    private static final java.util.Set<String> GENDER_SET = java.util.Set.of("男", "女");

    /** 年龄范围 */
    private static final int MIN_AGE = 1;
    private static final int MAX_AGE = 150;

    /** 分页大小上限 */
    private static final int MAX_PAGE_SIZE = 100;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addStudent(StudentRequest req) {
        // 参数校验
        validateStudentRequest(req);

        // 学号重复校验（注意：无法完全避免竞态，需数据库唯一索引兜底）
        boolean exists = lambdaQuery()
                .eq(Student::getStudentNo, req.getStudentNo())
                .exists();
        if (exists) {
            log.warn("新增学生失败：学号[{}]已存在", req.getStudentNo());
            throw new BusinessException("学号已存在");
        }

        // 构建学生对象，初始化 enabled 字段
        Student student = buildStudent(req);
        student.setEnabled(true); // 新增学生默认启用

        save(student);
        log.info("新增学生成功：id={}, 学号={}", student.getId(), student.getStudentNo());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStudent(Long id, StudentRequest req) {
        // 参数校验
        validateStudentRequest(req);

        // 检查学生是否存在
        Student existing = getById(id);
        if (existing == null) {
            log.warn("更新学生失败：学生[id={}]不存在", id);
            throw new BusinessException("学生不存在");
        }

        // 学号重复校验（排除自身）
        if (StringUtils.hasText(req.getStudentNo()) && !req.getStudentNo().equals(existing.getStudentNo())) {
            boolean conflict = lambdaQuery()
                    .eq(Student::getStudentNo, req.getStudentNo())
                    .ne(Student::getId, id)
                    .exists();
            if (conflict) {
                log.warn("更新学生失败：学号[{}]已被其他学生使用", req.getStudentNo());
                throw new BusinessException("学号已被其他学生使用");
            }
        }

        // 精准更新：只更新有值的字段
        LambdaUpdateWrapper<Student> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Student::getId, id);

        if (StringUtils.hasText(req.getName())) {
            updateWrapper.set(Student::getName, req.getName());
        }
        if (req.getGender() != null) {
            updateWrapper.set(Student::getGender, req.getGender());
        }
        if (req.getAge() != null) {
            updateWrapper.set(Student::getAge, req.getAge());
        }
        if (StringUtils.hasText(req.getStudentNo())) {
            updateWrapper.set(Student::getStudentNo, req.getStudentNo());
        }
        if (StringUtils.hasText(req.getMajor())) {
            updateWrapper.set(Student::getMajor, req.getMajor());
        }
        if (StringUtils.hasText(req.getEmail())) {
            updateWrapper.set(Student::getEmail, req.getEmail());
        }
        if (StringUtils.hasText(req.getPhone())) {
            updateWrapper.set(Student::getPhone, req.getPhone());
        }

        boolean updated = update(updateWrapper);
        if (!updated) {
            log.error("更新学生失败：id={}", id);
            throw new BusinessException("更新失败");
        }
        log.info("更新学生成功：id={}", id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteStudent(Long id) {
        boolean removed = removeById(id);
        if (!removed) {
            log.warn("删除学生失败：学生[id={}]不存在", id);
            throw new BusinessException("学生不存在");
        }
        log.info("删除学生成功：id={}", id);
    }

    @Override
    public Student getStudent(Long id) {
        Student student = getById(id);
        if (student == null) {
            log.warn("查询学生失败：学生[id={}]不存在", id);
            throw new BusinessException("学生不存在");
        }
        return student;
    }

    @Override
    public PageResult<Student> pageStudents(String name, String major, int page, int pageSize) {
        // 分页大小上限限制
        int actualPageSize = Math.min(pageSize, MAX_PAGE_SIZE);
        if (pageSize > MAX_PAGE_SIZE) {
            log.info("pageSize[{}]超过上限，已限制为{}", pageSize, MAX_PAGE_SIZE);
        }

        // 统一判空策略：使用 StringUtils.hasText()
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<Student>()
                .like(StringUtils.hasText(name), Student::getName, name)
                .like(StringUtils.hasText(major), Student::getMajor, major)
                .orderByDesc(Student::getId);

        Page<Student> result = page(new Page<>(page, actualPageSize), wrapper);
        return new PageResult<>(result.getTotal(), page, actualPageSize, result.getRecords());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void enableStudent(Long id) {
        Student student = getById(id);
        if (student == null) {
            log.warn("启用学生失败：学生[id={}]不存在", id);
            throw new BusinessException("学生不存在");
        }
        student.setEnabled(true);
        updateById(student);
        log.info("启用学生成功：id={}", id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void disableStudent(Long id) {
        Student student = getById(id);
        if (student == null) {
            log.warn("禁用学生失败：学生[id={}]不存在", id);
            throw new BusinessException("学生不存在");
        }
        student.setEnabled(false);
        updateById(student);
        log.info("禁用学生成功：id={}", id);
    }

    /**
     * 构建学生对象
     */
    private Student buildStudent(StudentRequest req) {
        Student student = new Student();
        student.setName(req.getName());
        student.setGender(req.getGender());
        student.setAge(req.getAge());
        student.setStudentNo(req.getStudentNo());
        student.setMajor(req.getMajor());
        student.setEmail(req.getEmail());
        student.setPhone(req.getPhone());
        return student;
    }

    /**
     * 参数校验
     */
    private void validateStudentRequest(StudentRequest req) {
        // 姓名必填
        if (!StringUtils.hasText(req.getName())) {
            throw new BusinessException("姓名不能为空");
        }
        // 学号必填
        if (!StringUtils.hasText(req.getStudentNo())) {
            throw new BusinessException("学号不能为空");
        }
        // 性别枚举约束
        if (req.getGender() != null && !GENDER_SET.contains(req.getGender())) {
            throw new BusinessException("性别只能是'男'或'女'");
        }
        // 年龄范围校验
        if (req.getAge() != null) {
            if (req.getAge() < MIN_AGE || req.getAge() > MAX_AGE) {
                throw new BusinessException("年龄必须在" + MIN_AGE + "-" + MAX_AGE + "之间");
            }
        }
    }
}