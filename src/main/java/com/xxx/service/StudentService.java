package com.xxx.service;

import com.xxx.dto.StudentRequest;
import com.xxx.entity.Student;
import com.xxx.mapper.StudentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentMapper studentMapper;

    public void addStudent(StudentRequest req) {
        if (studentMapper.findByStudentNo(req.getStudentNo()) != null) {
            throw new RuntimeException("学号已存在");
        }
        Student student = buildStudent(req);
        studentMapper.insert(student);
    }

    public void updateStudent(Long id, StudentRequest req) {
        Student existing = studentMapper.findById(id);
        if (existing == null) {
            throw new RuntimeException("学生不存在");
        }
        if (req.getStudentNo() != null) {
            Student byNo = studentMapper.findByStudentNo(req.getStudentNo());
            if (byNo != null && !byNo.getId().equals(id)) {
                throw new RuntimeException("学号已被其他学生使用");
            }
        }
        applyUpdate(existing, req);
        studentMapper.update(existing);
    }

    public void deleteStudent(Long id) {
        if (studentMapper.findById(id) == null) {
            throw new RuntimeException("学生不存在");
        }
        studentMapper.deleteById(id);
    }

    public Student getStudent(Long id) {
        Student student = studentMapper.findById(id);
        if (student == null) {
            throw new RuntimeException("学生不存在");
        }
        return student;
    }

    public com.xxx.common.PageResult<Student> listStudents(String name, String major, int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        List<Student> list = studentMapper.findAll(name, major, offset, pageSize);
        long total = studentMapper.countAll(name, major);
        return new com.xxx.common.PageResult<>(total, page, pageSize, list);
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
        if (req.getName() != null) existing.setName(req.getName());
        if (req.getGender() != null) existing.setGender(req.getGender());
        if (req.getAge() != null) existing.setAge(req.getAge());
        if (req.getStudentNo() != null) existing.setStudentNo(req.getStudentNo());
        if (req.getMajor() != null) existing.setMajor(req.getMajor());
        if (req.getEmail() != null) existing.setEmail(req.getEmail());
        if (req.getPhone() != null) existing.setPhone(req.getPhone());
    }
}