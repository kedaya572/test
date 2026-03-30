package com.xxx.student.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxx.common.exception.BusinessException;
import com.xxx.common.result.PageResult;
import com.xxx.student.dto.StudentRequest;
import com.xxx.student.entity.Student;
import com.xxx.student.mapper.StudentMapper;
import com.xxx.student.service.StudentService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDeleteStudents(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            throw new BusinessException("ID列表不能为空");
        }
        boolean removed = removeByIds(ids);
        if (!removed) {
            log.warn("批量删除学生失败：ids={}", ids);
            throw new BusinessException("批量删除失败，请确认ID列表是否正确");
        }
        log.info("批量删除学生成功：ids={}", ids);
    }

    @Override
    public List<Student> searchByName(String name) {
        if (!StringUtils.hasText(name)) {
            throw new BusinessException("姓名关键字不能为空");
        }
        List<Student> students = lambdaQuery()
                .like(Student::getName, name)
                .orderByDesc(Student::getId)
                .list();
        log.info("按姓名模糊查询：name={}, 结果数={}", name, students.size());
        return students;
    }

    @Override
    public List<Student> getByAgeRange(Integer minAge, Integer maxAge) {
        if (minAge == null || maxAge == null) {
            throw new BusinessException("年龄范围参数不能为空");
        }
        if (minAge > maxAge) {
            throw new BusinessException("最小年龄不能大于最大年龄");
        }
        if (minAge < MIN_AGE || maxAge > MAX_AGE) {
            throw new BusinessException("年龄范围必须在" + MIN_AGE + "-" + MAX_AGE + "之间");
        }
        List<Student> students = lambdaQuery()
                .ge(Student::getAge, minAge)
                .le(Student::getAge, maxAge)
                .orderByAsc(Student::getAge)
                .list();
        log.info("按年龄范围查询：minAge={}, maxAge={}, 结果数={}", minAge, maxAge, students.size());
        return students;
    }

    @Override
    public Map<String, Long> countByMajor() {
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.select("major, count(*) as cnt")
                .isNotNull("major")
                .ne("major", "")
                .groupBy("major")
                .orderByDesc("cnt");
        List<Map<String, Object>> rawList = listMaps(wrapper);
        Map<String, Long> result = new LinkedHashMap<>(rawList.size());
        for (Map<String, Object> row : rawList) {
            String major = (String) row.get("major");
            Long count = ((Number) row.get("cnt")).longValue();
            result.put(major, count);
        }
        log.info("统计专业学生数：共{}个专业", result.size());
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchUpdateStatus(List<Long> ids, Boolean enabled) {
        if (CollectionUtils.isEmpty(ids)) {
            throw new BusinessException("ID列表不能为空");
        }
        if (enabled == null) {
            throw new BusinessException("状态不能为空");
        }
        LambdaUpdateWrapper<Student> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.in(Student::getId, ids)
                .set(Student::getEnabled, enabled);
        boolean updated = update(updateWrapper);
        if (!updated) {
            log.warn("批量更新学生状态失败：ids={}, enabled={}", ids, enabled);
            throw new BusinessException("批量更新状态失败，请确认ID列表是否正确");
        }
        log.info("批量更新学生状态成功：ids={}, enabled={}", ids, enabled);
    }

    @Override
    public void exportStudents(HttpServletResponse response) {
        // TODO: 集成 EasyExcel 实现完整导出逻辑
        // 示例：查询全量数据写入响应流
        List<Student> students = list(new LambdaQueryWrapper<Student>().orderByDesc(Student::getId));
        log.info("导出学生数据：共{}条", students.size());
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=students.xlsx");
            // 实际项目中调用 EasyExcel.write(response.getOutputStream(), Student.class).sheet("学生列表").doWrite(students);
            response.getOutputStream().flush();
        } catch (Exception e) {
            log.error("导出学生Excel失败", e);
            throw new BusinessException("导出失败");
        }
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