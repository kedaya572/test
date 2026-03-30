package com.xxx.student.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxx.common.result.PageResult;
import com.xxx.student.dto.StudentRequest;
import com.xxx.student.entity.Student;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Map;

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

    /**
     * 启用学生
     *
     * @param id 学生 ID
     */
    void enableStudent(Long id);

    /**
     * 禁用学生
     *
     * @param id 学生 ID
     */
    void disableStudent(Long id);

    /**
     * 批量删除学生（逻辑删除）
     *
     * @param ids 学生 ID 列表
     */
    void batchDeleteStudents(List<Long> ids);

    /**
     * 按姓名模糊查询学生列表
     *
     * @param name 姓名关键字
     * @return 学生列表
     */
    List<Student> searchByName(String name);

    /**
     * 按年龄范围查询学生列表
     *
     * @param minAge 最小年龄（含）
     * @param maxAge 最大年龄（含）
     * @return 学生列表
     */
    List<Student> getByAgeRange(Integer minAge, Integer maxAge);

    /**
     * 统计各专业学生人数
     *
     * @return key=专业名称，value=学生人数
     */
    Map<String, Long> countByMajor();

    /**
     * 批量更新学生状态
     *
     * @param ids     学生 ID 列表
     * @param enabled 目标状态
     */
    void batchUpdateStatus(List<Long> ids, Boolean enabled);

    /**
     * 导出学生数据到 Excel
     *
     * @param response HTTP 响应
     */
    void exportStudents(HttpServletResponse response);
}
