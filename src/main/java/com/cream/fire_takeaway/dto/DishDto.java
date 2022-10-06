package com.cream.fire_takeaway.dto;

import com.cream.fire_takeaway.entity.DishEntity;
import com.cream.fire_takeaway.entity.DishFlavorEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Cream
 * @Date: 2022-09-27-15:06
 * @Description:
 */
@Data
public class DishDto extends DishEntity {

    //菜品口味
    private List<DishFlavorEntity> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
