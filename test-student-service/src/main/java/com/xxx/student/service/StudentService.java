package com.xxx.student.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxx.common.result.PageResult;
import com.xxx.student.dto.StudentRequest;
import com.xxx.student.entity.Student;

/**
 * 学生服务接口
 */
public interface StudentService extends IService<Student> {

    /**
     * 新增学生
     */
    void addStudent(StudentRequest req);

    /**
     * 更新学生信息
     *
     * @param id  学生 ID
     * @param req 更新内容
     */
    void updateStudent(Long id, StudentRequest req);

    /**
     * 删除学生（逻辑删除）
     *
     * @param id 学生 ID
     */
    void deleteStudent(Long id);

    /**
     * 查询单个学生
     *
     * @param id 学生 ID
     * @return 学生信息
     */
    Student getStudent(Long id);

    /**
     * 分页查询学生列表
     *
     * @param name     姓名（模糊匹配，可为空）
     * @param major    专业（模糊匹配，可为空）
     * @param page     当前页码（从 1 开始）
     * @param pageSize 每页条数
     * @return 分页结果
     */
    PageResult<Student> pageStudents(String name, String major, int page, int pageSize);
}
