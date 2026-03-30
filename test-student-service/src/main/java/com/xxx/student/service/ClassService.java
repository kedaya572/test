package com.xxx.student.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxx.common.result.PageResult;
import com.xxx.student.dto.ClassQueryRequest;
import com.xxx.student.dto.ClassRequest;
import com.xxx.student.entity.Clazz;

import java.util.List;

/**
 * 班级服务接口
 *
 * @author shuimen
 */
public interface ClassService extends IService<Clazz> {

    /**
     * 新增班级
     *
     * @param req 新增请求
     */
    void addClass(ClassRequest req);

    /**
     * 更新班级信息
     *
     * @param id  班级 ID
     * @param req 更新内容
     */
    void updateClass(Long id, ClassRequest req);

    /**
     * 删除班级（逻辑删除）
     *
     * @param id 班级 ID
     */
    void deleteClass(Long id);

    /**
     * 查询单个班级
     *
     * @param id 班级 ID
     * @return 班级信息
     */
    Clazz getClass(Long id);

    /**
     * 批量删除班级（逻辑删除）
     *
     * @param ids 班级 ID 列表
     */
    void batchDeleteClasses(List<Long> ids);

    /**
     * 根据 ID 集合批量查询班级
     *
     * @param ids 班级 ID 列表
     * @return 班级列表
     */
    List<Clazz> listClassesByIds(List<Long> ids);

    /**
     * 分页查询班级列表
     *
     * @param req 查询条件（支持模糊/精确匹配及分页参数）
     * @return 分页结果
     */
    PageResult<Clazz> pageClasses(ClassQueryRequest req);
}
