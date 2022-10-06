package com.cream.fire_takeaway.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cream.fire_takeaway.dto.DishDto;
import com.cream.fire_takeaway.entity.DishEntity;
import com.cream.fire_takeaway.entity.DishFlavorEntity;

import java.util.List;

/**
 * @Author: Cream
 * @Date: 2022-09-26-14:21
 * @Description:
 */
public interface DishFlavorService extends IService<DishFlavorEntity> {

    List<DishFlavorEntity> list(Long dishId);

    void saveBatch(Long dishId, List<DishFlavorEntity> dishFlavorEntities);

    void deleteByDishId(Long dishId);

    List<DishDto> getDishDtoByDish(List<DishEntity> list);
}
