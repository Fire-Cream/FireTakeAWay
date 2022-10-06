package com.cream.fire_takeaway.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cream.fire_takeaway.dto.DishDto;
import com.cream.fire_takeaway.dto.SetmealDto;
import com.cream.fire_takeaway.entity.CategoryEntity;
import com.cream.fire_takeaway.entity.DishEntity;
import com.cream.fire_takeaway.entity.SetmealEntity;
import com.cream.fire_takeaway.exception.CustomException;
import com.cream.fire_takeaway.mapper.CategoryMapper;
import com.cream.fire_takeaway.service.CategoryService;
import com.cream.fire_takeaway.service.DishService;
import com.cream.fire_takeaway.service.SetmealService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Cream
 * @Date: 2022-09-25-20:31
 * @Description:
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, CategoryEntity> implements CategoryService {

    @Override
    public IPage<CategoryEntity> getCategorys(Page<CategoryEntity> page, String name) {
        LambdaQueryWrapper<CategoryEntity> queryWrapper = new LambdaQueryWrapper<CategoryEntity>();
        queryWrapper.orderByAsc(CategoryEntity::getSort);
        queryWrapper.eq(name != null, CategoryEntity::getName, name);
        return baseMapper.selectPage(page, queryWrapper);
    }

    @Override
    public List<CategoryEntity> getListByType(Integer type) {
        LambdaQueryWrapper<CategoryEntity> lambdaQueryWrapper = new LambdaQueryWrapper<CategoryEntity>();
        lambdaQueryWrapper.eq(type != null, CategoryEntity::getType, type);
        lambdaQueryWrapper.orderByAsc(CategoryEntity::getSort).orderByAsc(CategoryEntity::getUpdateTime);
        return baseMapper.selectList(lambdaQueryWrapper);
    }

    @Override
    public List<DishDto> getDishDtoByDish(List<DishEntity> dishEntitiess) {
        return dishEntitiess.stream().map((item) -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            Long cateGoryId = item.getCategoryId();
            CategoryEntity categoryEntity = baseMapper.selectById(cateGoryId);
            dishDto.setCategoryName(categoryEntity.getName());
            return dishDto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<SetmealDto> getSetmealDtoBySetmeal(List<SetmealEntity> setmealEntities) {
        return setmealEntities.stream().map((item) -> {
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item, setmealDto);
            Long categoryId = item.getCategoryId();
            CategoryEntity categoryEntity = baseMapper.selectById(categoryId);
            setmealDto.setCategoryName(categoryEntity.getName());
            return setmealDto;
        }).collect(Collectors.toList());
    }
}
