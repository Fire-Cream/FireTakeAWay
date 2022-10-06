package com.cream.fire_takeaway.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cream.fire_takeaway.entity.SetmealDishEntity;
import com.cream.fire_takeaway.entity.SetmealEntity;

import java.util.List;

/**
 * @Author: Cream
 * @Date: 2022-09-26-14:23
 * @Description:
 */
public interface SetmealDishService extends IService<SetmealDishEntity> {
    List<SetmealDishEntity> getSetmealDishBySetmealId(Long id);

    void saveBatch(Long id, List<SetmealDishEntity> setmealDishes);

    void deleteBySetmealId(Long id);

    void deleteBySetmealIds(Long[] ids);

}
