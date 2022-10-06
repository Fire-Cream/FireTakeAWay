package com.cream.fire_takeaway.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cream.fire_takeaway.entity.DishEntity;

import java.util.List;

/**
 * @Author: Cream
 * @Date: 2022-09-26-14:20
 * @Description:
 */
public interface DishService extends IService<DishEntity> {
    IPage<DishEntity> getDishes(Page<DishEntity> p, String name);

    DishEntity getDishInfoById(Long dishId);

    Long count(Long id);

    void changeStatus(Integer status, Long[] ids);

    void deleteDish(Long[] ids);

    List<DishEntity> getDishByDish(DishEntity dish);
}
