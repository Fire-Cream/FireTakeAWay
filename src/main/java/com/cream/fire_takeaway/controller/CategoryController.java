package com.cream.fire_takeaway.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cream.fire_takeaway.common.R;
import com.cream.fire_takeaway.entity.CategoryEntity;
import com.cream.fire_takeaway.exception.CustomException;
import com.cream.fire_takeaway.service.CategoryService;
import com.cream.fire_takeaway.service.DishService;
import com.cream.fire_takeaway.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Cream
 * @Date: 2022-09-25-20:27
 * @Description:
 */
@RestController
@RequestMapping("/category")
@Transactional
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;


    @GetMapping("/page")
    public R getCategorys(@RequestParam("page") Long page,
                          @RequestParam("pageSize") Long pageSize,
                          @RequestParam(value = "name", required = false) String name
    ) {
        Page<CategoryEntity> p = new Page<>(page, pageSize);

        IPage<CategoryEntity> pages = categoryService.getCategorys(p, name);

        return R.success(pages);
    }

    @PostMapping
    public R addCategory(@RequestBody CategoryEntity category) {
        categoryService.save(category);
        return R.success("添加成功");
    }

    @PutMapping
    public R changeCategory(@RequestBody CategoryEntity category) {
        categoryService.updateById(category);
        return R.success("修改成功");
    }

    @DeleteMapping
    public R deleteCategory(@RequestParam("ids") Long id) {
        Long dishCount = dishService.count(id);
        if (dishCount > 0) {
            throw new CustomException("当前分类下关联了菜品，不能删除");
        }
        Long setmealCount = setmealService.count(id);
        if (setmealCount > 0) {
            throw new CustomException("当前分类下关联了套餐，不能删除");
        }
        categoryService.removeById(id);
        return R.success("删除成功");
    }

    @GetMapping("/list")
    public R CategoryList(@RequestParam(value = "type", required = false) Integer type) {
        List<CategoryEntity> list = categoryService.getListByType(type);
        return R.success(list);
    }

}
