package com.xxx.student.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxx.common.exception.BusinessException;
import com.xxx.common.result.PageResult;
import com.xxx.student.dto.ClassQueryRequest;
import com.xxx.student.dto.ClassRequest;
import com.xxx.student.entity.Clazz;
import com.xxx.student.mapper.ClassMapper;
import com.xxx.student.service.ClassService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * 班级服务实现
 *
 * @author shuimen
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ClassServiceImpl extends ServiceImpl<ClassMapper, Clazz> implements ClassService {

    /** 分页大小上限 */
    private static final int MAX_PAGE_SIZE = 100;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addClass(ClassRequest req) {
        // 班级名称重复校验（注意：无法完全避免竞态，需数据库唯一索引兜底）
        boolean exists = lambdaQuery()
                .eq(Clazz::getClassName, req.getClassName())
                .eq(Clazz::getGrade, req.getGrade())
                .exists();
        if (exists) {
            log.warn("新增班级失败：年级[{}]下班级名称[{}]已存在", req.getGrade(), req.getClassName());
            throw new BusinessException("该年级下班级名称已存在");
        }

        Clazz clazz = buildClazz(req);
        save(clazz);
        log.info("新增班级成功：id={}, 班级名称={}, 年级={}", clazz.getId(), clazz.getClassName(), clazz.getGrade());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateClass(Long id, ClassRequest req) {
        // 检查班级是否存在
        Clazz existing = getById(id);
        if (existing == null) {
            log.warn("更新班级失败：班级[id={}]不存在", id);
            throw new BusinessException("班级不存在");
        }

        // 班级名称重复校验（排除自身，同年级下不允许重名）
        Integer targetGrade = req.getGrade() != null ? req.getGrade() : existing.getGrade();
        if (StringUtils.hasText(req.getClassName()) && !req.getClassName().equals(existing.getClassName())) {
            boolean conflict = lambdaQuery()
                    .eq(Clazz::getClassName, req.getClassName())
                    .eq(Clazz::getGrade, targetGrade)
                    .ne(Clazz::getId, id)
                    .exists();
            if (conflict) {
                log.warn("更新班级失败：年级[{}]下班级名称[{}]已被其他班级使用", targetGrade, req.getClassName());
                throw new BusinessException("该年级下班级名称已被其他班级使用");
            }
        }

        // 精准更新：只更新有值的字段
        LambdaUpdateWrapper<Clazz> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Clazz::getId, id);

        if (StringUtils.hasText(req.getClassName())) {
            updateWrapper.set(Clazz::getClassName, req.getClassName());
        }
        if (req.getGrade() != null) {
            updateWrapper.set(Clazz::getGrade, req.getGrade());
        }
        if (req.getTeacherId() != null) {
            updateWrapper.set(Clazz::getTeacherId, req.getTeacherId());
        }

        boolean updated = update(updateWrapper);
        if (!updated) {
            log.error("更新班级失败：id={}", id);
            throw new BusinessException("更新失败");
        }
        log.info("更新班级成功：id={}", id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteClass(Long id) {
        boolean removed = removeById(id);
        if (!removed) {
            log.warn("删除班级失败：班级[id={}]不存在", id);
            throw new BusinessException("班级不存在");
        }
        log.info("删除班级成功：id={}", id);
    }

    @Override
    public Clazz getClass(Long id) {
        Clazz clazz = getById(id);
        if (clazz == null) {
            log.warn("查询班级失败：班级[id={}]不存在", id);
            throw new BusinessException("班级不存在");
        }
        return clazz;
    }

    @Override
    public PageResult<Clazz> pageClasses(ClassQueryRequest req) {
        int actualPageSize = Math.min(req.getPageSize(), MAX_PAGE_SIZE);
        if (req.getPageSize() > MAX_PAGE_SIZE) {
            log.info("pageSize[{}]超过上限，已限制为{}", req.getPageSize(), MAX_PAGE_SIZE);
        }

        LambdaQueryWrapper<Clazz> wrapper = new LambdaQueryWrapper<Clazz>()
                .like(StringUtils.hasText(req.getClassName()), Clazz::getClassName, req.getClassName())
                .eq(req.getGrade() != null, Clazz::getGrade, req.getGrade())
                .eq(req.getTeacherId() != null, Clazz::getTeacherId, req.getTeacherId())
                .orderByDesc(Clazz::getId);

        Page<Clazz> result = page(new Page<>(req.getPage(), actualPageSize), wrapper);
        log.info("分页查询班级列表：page={}, pageSize={}, total={}", req.getPage(), actualPageSize, result.getTotal());
        return new PageResult<>(result.getTotal(), req.getPage(), actualPageSize, result.getRecords());
    }

    /**
     * 构建班级对象
     */
    private Clazz buildClazz(ClassRequest req) {
        Clazz clazz = new Clazz();
        clazz.setClassName(req.getClassName());
        clazz.setGrade(req.getGrade());
        clazz.setTeacherId(req.getTeacherId());
        return clazz;
    }
}
