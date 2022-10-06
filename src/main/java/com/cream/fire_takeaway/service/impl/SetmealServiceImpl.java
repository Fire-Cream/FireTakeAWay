package com.cream.fire_takeaway.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cream.fire_takeaway.entity.SetmealEntity;
import com.cream.fire_takeaway.exception.CustomException;
import com.cream.fire_takeaway.mapper.SetmealMapper;
import com.cream.fire_takeaway.service.SetmealService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: Cream
 * @Date: 2022-09-26-14:30
 * @Description:
 */
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, SetmealEntity> implements SetmealService {
    @Override
    public Long count(Long id) {
        LambdaQueryWrapper<SetmealEntity> lambdaQueryWrapper = new LambdaQueryWrapper<SetmealEntity>();
        lambdaQueryWrapper.eq(SetmealEntity::getCategoryId, id);
        return baseMapper.selectCount(lambdaQueryWrapper);
    }

    @Override
    public IPage<SetmealEntity> getSetmeals(Page<SetmealEntity> p, String name) {
        LambdaQueryWrapper<SetmealEntity> lambdaQueryWrapper = new LambdaQueryWrapper<SetmealEntity>();

        lambdaQueryWrapper.eq(name != null, SetmealEntity::getName, name);

        lambdaQueryWrapper.eq(SetmealEntity::getIsDeleted, 0);

        lambdaQueryWrapper.orderByAsc(SetmealEntity::getUpdateTime);

        return baseMapper.selectPage(p, lambdaQueryWrapper);
    }

    @Override
    public void deleteByIds(Long[] ids) {
        LambdaQueryWrapper<SetmealEntity> lambdaQueryWrapper = new LambdaQueryWrapper<SetmealEntity>();
        lambdaQueryWrapper.in(SetmealEntity::getId, ids);
        lambdaQueryWrapper.eq(SetmealEntity::getStatus, 1);
        Long count = baseMapper.selectCount(lambdaQueryWrapper);
        if (count > 0) {
            throw new CustomException("套餐正在售卖中，不能删除");
        }
        baseMapper.deleteBatchIds(Arrays.asList(ids));
    }

    @Override
    public void changeStatusByIds(Integer status, Long[] ids) {
        for (Long id : ids) {
            SetmealEntity setmealEntity = new SetmealEntity();
            setmealEntity.setId(id);
            setmealEntity.setStatus(status);
            baseMapper.updateById(setmealEntity);
        }
    }

    @Override
    public List<SetmealEntity> getSetmealBySetmeal(SetmealEntity setmeal) {
        LambdaQueryWrapper<SetmealEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SetmealEntity::getCategoryId, setmeal.getCategoryId());
        lambdaQueryWrapper.eq(setmeal.getStatus() != null, SetmealEntity::getStatus, setmeal.getStatus());
        return baseMapper.selectList(lambdaQueryWrapper);
    }

}
