package com.cream.fire_takeaway.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cream.fire_takeaway.entity.SetmealEntity;

import java.util.List;

/**
 * @Author: Cream
 * @Date: 2022-09-26-14:23
 * @Description:
 */
public interface SetmealService extends IService<SetmealEntity> {

    Long count(Long id);

    IPage<SetmealEntity> getSetmeals(Page<SetmealEntity> p, String name);

    void deleteByIds(Long[] ids);

    void changeStatusByIds(Integer status, Long[] ids);

    List<SetmealEntity> getSetmealBySetmeal(SetmealEntity setmeal);
}
