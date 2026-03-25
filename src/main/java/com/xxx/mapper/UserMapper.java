package com.xxx.mapper;

import com.xxx.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User findByUsername(String username);
    void insert(User user);
}