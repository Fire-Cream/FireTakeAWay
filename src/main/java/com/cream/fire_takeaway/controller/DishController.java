package com.cream.fire_takeaway.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cream.fire_takeaway.common.R;
import com.cream.fire_takeaway.dto.DishDto;
import com.cream.fire_takeaway.entity.CategoryEntity;
import com.cream.fire_takeaway.entity.DishEntity;
import com.cream.fire_takeaway.entity.DishFlavorEntity;
import com.cream.fire_takeaway.service.CategoryService;
import com.cream.fire_takeaway.service.DishFlavorService;
import com.cream.fire_takeaway.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Cream
 * @Date: 2022-09-26-16:47
 * @Description:
 */
@RestController
@RequestMapping("/dish")
@Transactional
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    DishFlavorService dishFlavorService;

    @Autowired
    CategoryService categoryService;

    @GetMapping("/page")
    public R page(@RequestParam("page") Long page,
                  @RequestParam("pageSize") Long pageSize,
                  @RequestParam(value = "name", required = false) String name
    ) {
        Page<DishEntity> p = new Page<DishEntity>(page, pageSize);

        IPage<DishEntity> pages = dishService.getDishes(p, name);

        IPage<DishDto> dtoPage = new Page<DishDto>();

        BeanUtils.copyProperties(pages, dtoPage, "records");

        List<DishDto> dishDtos = categoryService.getDishDtoByDish(pages.getRecords());

        dtoPage.setRecords(dishDtos);

        return R.success(dtoPage);
    }

    @PostMapping
    public R addDish(@RequestBody DishDto dishDto) {

        dishService.save(dishDto);

        dishFlavorService.saveBatch(dishDto.getId(), dishDto.getFlavors());

        return R.success("添加成功");
    }

    @PutMapping
    public R updateDish(@RequestBody DishDto dishDto) {
        dishService.saveOrUpdate(dishDto);

        dishFlavorService.deleteByDishId(dishDto.getId());

        dishFlavorService.saveBatch(dishDto.getId(), dishDto.getFlavors());

        return R.success("修改成功");
    }

    @GetMapping("/{dishId}")
    public R dishInfo(@PathVariable("dishId") Long dishId) {

        DishEntity dishEntity = dishService.getDishInfoById(dishId);

        DishDto dishDto = new DishDto();

        BeanUtils.copyProperties(dishEntity, dishDto);

        List<DishFlavorEntity> dishFlavorEntities = dishFlavorService.list(dishDto.getId());

        dishDto.setFlavors(dishFlavorEntities);

        return R.success(dishDto);
    }

    @PostMapping("/status/{status}")
    public R changeStatus(@PathVariable("status") Integer status, @RequestParam("ids") Long[] ids) {

        dishService.changeStatus(status, ids);

        return R.success("修改成功");
    }

    @DeleteMapping
    public R deleteDish(@RequestParam("ids") Long[] ids) {

        dishService.deleteDish(ids);

        return R.success("删除成功");
    }

    @GetMapping("/list")
    public R getDishByCategoryId(DishEntity dish) {
        List<DishEntity> list = dishService.getDishByDish(dish);

        List<DishDto> dtoList = dishFlavorService.getDishDtoByDish(list);
        return R.success(dtoList);
    }

//    @GetMapping("/list")
//    public R getDishByCategortId(@RequestParam("categoryId") Long categoryId,
//                                 @RequestParam(value = "status",required = false,defaultValue = "1") Integer status){
//        List<DishEntity> list = dishService.getDishByCategortId(categoryId,status);
//        return R.success(list);
//    }
}
