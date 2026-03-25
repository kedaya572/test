package com.xxx.mapper;

import com.xxx.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface StudentMapper {
    void insert(Student student);
    void update(Student student);
    void deleteById(Long id);
    Student findById(Long id);
    Student findByStudentNo(String studentNo);
    List<Student> findAll(@Param("name") String name, @Param("major") String major, 
                         @Param("offset") int offset, @Param("limit") int limit);
    long countAll(@Param("name") String name, @Param("major") String major);
}