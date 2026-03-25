package com.xxx.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxx.auth.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户 Mapper
 *
 * <p>继承 BaseMapper 即可获得全套 CRUD 方法，无需编写 XML。</p>
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
