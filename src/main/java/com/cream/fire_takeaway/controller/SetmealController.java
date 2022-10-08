package com.cream.fire_takeaway.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cream.fire_takeaway.common.R;
import com.cream.fire_takeaway.dto.SetmealDto;
import com.cream.fire_takeaway.entity.SetmealDishEntity;
import com.cream.fire_takeaway.entity.SetmealEntity;
import com.cream.fire_takeaway.service.CategoryService;
import com.cream.fire_takeaway.service.DishService;
import com.cream.fire_takeaway.service.SetmealDishService;
import com.cream.fire_takeaway.service.SetmealService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Cream
 * @Date: 2022-09-28-15:49
 * @Description:
 */
@RestController
@RequestMapping("/setmeal")
@Transactional
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SetmealDishService setmealDishService;

    @GetMapping("/page")
    public R page(@RequestParam("page") Long page,
                  @RequestParam("pageSize") Long pageSize,
                  @RequestParam(value = "name", required = false) String name) {

        //TODO 分类

        Page<SetmealEntity> p = new Page<>(page, pageSize);

        IPage<SetmealEntity> pages = setmealService.getSetmeals(p, name);

        IPage<SetmealDto> dtoIPage = new Page<SetmealDto>();

        BeanUtils.copyProperties(pages, dtoIPage, "records");

        List<SetmealDto> setmealDtos = categoryService.getSetmealDtoBySetmeal(pages.getRecords());

        dtoIPage.setRecords(setmealDtos);

        return R.success(dtoIPage);
    }

    @PostMapping
    @CacheEvict(value = "setmealCache", allEntries = true)
    public R addSetmeal(@RequestBody SetmealDto setmealDto) {
        setmealService.save(setmealDto);
        setmealDishService.saveBatch(setmealDto.getId(), setmealDto.getSetmealDishes());
        return R.success("添加成功");
    }

    @GetMapping("/{id}")
    public R getSetmealInfo(@PathVariable("id") Long id) {
        SetmealEntity setmealEntity = setmealService.getById(id);
        SetmealDto setmealDto = new SetmealDto();
        BeanUtils.copyProperties(setmealEntity, setmealDto);
        List<SetmealDishEntity> setmealDish = setmealDishService.getSetmealDishBySetmealId(setmealEntity.getId());
        setmealDto.setSetmealDishes(setmealDish);
        return R.success(setmealDto);
    }

    @PutMapping
    @CacheEvict(value = "setmealCache", allEntries = true)
    public R changeSetmeal(@RequestBody SetmealDto setmealDto) {
        setmealService.saveOrUpdate(setmealDto);

        setmealDishService.deleteBySetmealId(setmealDto.getId());

        setmealDishService.saveBatch(setmealDto.getId(), setmealDto.getSetmealDishes());

        return R.success("修改成功");
    }

    @DeleteMapping
    @CacheEvict(value = "setmealCache", allEntries = true)
    public R deleteSetmeal(@RequestParam("ids") Long[] ids) {
        setmealService.deleteByIds(ids);
        setmealDishService.deleteBySetmealIds(ids);
        return R.success("删除成功");
    }


    @PostMapping("/status/{status}")
    @CacheEvict(value = "setmealCache", allEntries = true)
    public R changeStatus(@PathVariable("status") Integer status,
                          @RequestParam("ids") Long[] ids) {
        setmealService.changeStatusByIds(status, ids);
        return R.success("修改成功");
    }

    @GetMapping("/list")
    @Cacheable(value = "setmealCache", key = "#setmeal.categoryId + '_' + #setmeal.status")
    public R getSetmealByCategortId(SetmealEntity setmeal) {
        List<SetmealEntity> list = setmealService.getSetmealBySetmeal(setmeal);
        return R.success(list);
    }
}
