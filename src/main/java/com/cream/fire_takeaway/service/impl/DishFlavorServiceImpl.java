package com.cream.fire_takeaway.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cream.fire_takeaway.dto.DishDto;
import com.cream.fire_takeaway.entity.DishEntity;
import com.cream.fire_takeaway.entity.DishFlavorEntity;
import com.cream.fire_takeaway.mapper.DishFlavorMapper;
import com.cream.fire_takeaway.service.DishFlavorService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Cream
 * @Date: 2022-09-26-14:26
 * @Description:
 */
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavorEntity> implements DishFlavorService {
    @Override
    public List<DishFlavorEntity> list(Long dishId) {
        LambdaQueryWrapper<DishFlavorEntity> flavorEntityLambdaQueryWrapper = new LambdaQueryWrapper<DishFlavorEntity>();
        flavorEntityLambdaQueryWrapper.eq(DishFlavorEntity::getDishId, dishId);
        return baseMapper.selectList(flavorEntityLambdaQueryWrapper);
    }

    @Override
    public void saveBatch(Long dishId, List<DishFlavorEntity> dishFlavorEntities) {
        dishFlavorEntities = dishFlavorEntities.stream().map((item) -> {
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());
        dishFlavorEntities.forEach((item) -> {
            baseMapper.insert(item);
        });
    }

    @Override
    public void deleteByDishId(Long dishId) {
        LambdaQueryWrapper<DishFlavorEntity> lambdaQueryWrapper = new LambdaQueryWrapper<DishFlavorEntity>();
        lambdaQueryWrapper.eq(DishFlavorEntity::getDishId, dishId);
        baseMapper.delete(lambdaQueryWrapper);
    }

    @Override
    public List<DishDto> getDishDtoByDish(List<DishEntity> list) {
        return list.stream().map((item) -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            LambdaQueryWrapper<DishFlavorEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(DishFlavorEntity::getDishId, item.getId());
            List<DishFlavorEntity> dishFlavorEntities = baseMapper.selectList(lambdaQueryWrapper);
            dishDto.setFlavors(dishFlavorEntities);
            return dishDto;
        }).collect(Collectors.toList());
    }

}
