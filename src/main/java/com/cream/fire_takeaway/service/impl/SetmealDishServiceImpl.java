package com.cream.fire_takeaway.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cream.fire_takeaway.entity.SetmealDishEntity;
import com.cream.fire_takeaway.entity.SetmealEntity;
import com.cream.fire_takeaway.mapper.SetmealDishMapper;
import com.cream.fire_takeaway.service.SetmealDishService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Cream
 * @Date: 2022-09-26-14:30
 * @Description:
 */
@Service
public class SetmealDishServiceImpl extends ServiceImpl<SetmealDishMapper, SetmealDishEntity> implements SetmealDishService {
    @Override
    public List<SetmealDishEntity> getSetmealDishBySetmealId(Long id) {
        LambdaQueryWrapper<SetmealDishEntity> lambdaQueryWrapper = new LambdaQueryWrapper<SetmealDishEntity>();
        lambdaQueryWrapper.eq(SetmealDishEntity::getSetmealId, id);
        return baseMapper.selectList(lambdaQueryWrapper);
    }

    @Override
    public void saveBatch(Long id, List<SetmealDishEntity> setmealDishes) {
        setmealDishes = setmealDishes.stream().map((item) -> {
            item.setSetmealId(id);
            return item;
        }).collect(Collectors.toList());

        setmealDishes.forEach((item) -> {
            baseMapper.insert(item);
        });
    }

    @Override
    public void deleteBySetmealId(Long id) {
        LambdaQueryWrapper<SetmealDishEntity> lambdaQueryWrapper = new LambdaQueryWrapper<SetmealDishEntity>();
        lambdaQueryWrapper.eq(SetmealDishEntity::getSetmealId, id);
        baseMapper.delete(lambdaQueryWrapper);
    }

    @Override
    public void deleteBySetmealIds(Long[] ids) {
        LambdaQueryWrapper<SetmealDishEntity> lambdaQueryWrapper = new LambdaQueryWrapper<SetmealDishEntity>();
        lambdaQueryWrapper.in(SetmealDishEntity::getSetmealId, ids);
        baseMapper.delete(lambdaQueryWrapper);
    }

}
