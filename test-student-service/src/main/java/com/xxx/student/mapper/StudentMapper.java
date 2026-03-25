package com.xxx.student.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxx.student.entity.Student;
import org.apache.ibatis.annotations.Mapper;

/**
 * 学生 Mapper
 *
 * <p>继承 BaseMapper 获得全套 CRUD 方法，分页/条件查询通过 MyBatis-Plus 构造器实现。</p>
 */
@Mapper
public interface StudentMapper extends BaseMapper<Student> {
}
