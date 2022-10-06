package com.cream.fire_takeaway.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cream.fire_takeaway.dto.DishDto;
import com.cream.fire_takeaway.dto.SetmealDto;
import com.cream.fire_takeaway.entity.CategoryEntity;
import com.cream.fire_takeaway.entity.DishEntity;
import com.cream.fire_takeaway.entity.SetmealEntity;

import java.util.List;

/**
 * @Author: Cream
 * @Date: 2022-09-25-20:30
 * @Description:
 */
public interface CategoryService extends IService<CategoryEntity> {
    IPage<CategoryEntity> getCategorys(Page<CategoryEntity> p, String name);

    List<CategoryEntity> getListByType(Integer type);

    List<DishDto> getDishDtoByDish(List<DishEntity> dishEntitiess);

    List<SetmealDto> getSetmealDtoBySetmeal(List<SetmealEntity> records);
}
