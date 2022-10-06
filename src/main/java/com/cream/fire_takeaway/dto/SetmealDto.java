package com.cream.fire_takeaway.dto;

import com.cream.fire_takeaway.entity.SetmealDishEntity;
import com.cream.fire_takeaway.entity.SetmealEntity;
import lombok.Data;

import java.util.List;

/**
 * @Author: Cream
 * @Date: 2022-09-27-15:09
 * @Description:
 */
@Data
public class SetmealDto extends SetmealEntity {
    private List<SetmealDishEntity> setmealDishes;

    private String categoryName;
}
