package com.cream.fire_takeaway.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cream.fire_takeaway.entity.DishEntity;
import com.cream.fire_takeaway.mapper.DishMapper;
import com.cream.fire_takeaway.service.DishService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Cream
 * @Date: 2022-09-26-14:27
 * @Description:
 */
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, DishEntity> implements DishService {

    @Override
    public IPage<DishEntity> getDishes(Page<DishEntity> p, String name) {
        LambdaQueryWrapper<DishEntity> lambdaQueryWrapper = new LambdaQueryWrapper<DishEntity>();
        lambdaQueryWrapper.eq(name != null, DishEntity::getName, name);
        lambdaQueryWrapper.eq(DishEntity::getIsDeleted, 0);
        return baseMapper.selectPage(p, lambdaQueryWrapper);
    }

    @Override
    public DishEntity getDishInfoById(Long dishId) {
        LambdaQueryWrapper<DishEntity> dishLambdaQueryWrapper = new LambdaQueryWrapper<DishEntity>();
        dishLambdaQueryWrapper.eq(DishEntity::getId, dishId);
        return baseMapper.selectOne(dishLambdaQueryWrapper);
    }

    @Override
    public Long count(Long id) {
        LambdaQueryWrapper<DishEntity> lambdaQueryWrapper = new LambdaQueryWrapper<DishEntity>();
        lambdaQueryWrapper.eq(DishEntity::getCategoryId, id);
        return baseMapper.selectCount(lambdaQueryWrapper);
    }

    @Override
    public void changeStatus(Integer status, Long[] ids) {
        for (Long id : ids) {
            DishEntity dish = new DishEntity();
            dish.setStatus(status);
            dish.setId(id);
            baseMapper.updateById(dish);
        }
    }

    @Override
    public void deleteDish(Long[] ids) {
        for (Long id : ids) {
            DishEntity dish = new DishEntity();
            dish.setIsDeleted(1);
            dish.setId(id);
            baseMapper.updateById(dish);
        }
    }

    @Override
    public List<DishEntity> getDishByDish(DishEntity dish) {
        LambdaQueryWrapper<DishEntity> lambdaQueryWrapper = new LambdaQueryWrapper<DishEntity>();
        lambdaQueryWrapper.eq(DishEntity::getCategoryId, dish.getCategoryId());
        lambdaQueryWrapper.eq(dish.getStatus() != null, DishEntity::getStatus, dish.getStatus());
        return baseMapper.selectList(lambdaQueryWrapper);
    }

}
