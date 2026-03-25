package com.xxx.student.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxx.common.exception.BusinessException;
import com.xxx.common.result.PageResult;
import com.xxx.student.dto.StudentRequest;
import com.xxx.student.entity.Student;
import com.xxx.student.mapper.StudentMapper;
import com.xxx.student.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 学生服务实现
 */
@Service
@RequiredArgsConstructor
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    @Override
    public void addStudent(StudentRequest req) {
        boolean exists = lambdaQuery()
                .eq(Student::getStudentNo, req.getStudentNo())
                .exists();
        if (exists) {
            throw new BusinessException("学号已存在");
        }
        Student student = buildStudent(req);
        save(student);
    }

    @Override
    public void updateStudent(Long id, StudentRequest req) {
        Student existing = getById(id);
        if (existing == null) {
            throw new BusinessException("学生不存在");
        }
        if (StringUtils.hasText(req.getStudentNo())) {
            boolean conflict = lambdaQuery()
                    .eq(Student::getStudentNo, req.getStudentNo())
                    .ne(Student::getId, id)
                    .exists();
            if (conflict) {
                throw new BusinessException("学号已被其他学生使用");
            }
        }
        applyUpdate(existing, req);
        updateById(existing);
    }

    @Override
    public void deleteStudent(Long id) {
        if (!removeById(id)) {
            throw new BusinessException("学生不存在");
        }
    }

    @Override
    public Student getStudent(Long id) {
        Student student = getById(id);
        if (student == null) {
            throw new BusinessException("学生不存在");
        }
        return student;
    }

    @Override
    public PageResult<Student> pageStudents(String name, String major, int page, int pageSize) {
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<Student>()
                .like(StringUtils.hasText(name), Student::getName, name)
                .like(StringUtils.hasText(major), Student::getMajor, major)
                .orderByDesc(Student::getId);

        Page<Student> result = page(new Page<>(page, pageSize), wrapper);
        return new PageResult<>(result.getTotal(), page, pageSize, result.getRecords());
    }

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

    private void applyUpdate(Student existing, StudentRequest req) {
        if (StringUtils.hasText(req.getName())) {
            existing.setName(req.getName());
        }
        if (req.getGender() != null) {
            existing.setGender(req.getGender());
        }
        if (req.getAge() != null) {
            existing.setAge(req.getAge());
        }
        if (StringUtils.hasText(req.getStudentNo())) {
            existing.setStudentNo(req.getStudentNo());
        }
        if (req.getMajor() != null) {
            existing.setMajor(req.getMajor());
        }
        if (req.getEmail() != null) {
            existing.setEmail(req.getEmail());
        }
        if (req.getPhone() != null) {
            existing.setPhone(req.getPhone());
        }
    }
}
