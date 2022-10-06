package com.cream.fire_takeaway.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cream.fire_takeaway.entity.EmployeeEntity;

/**
 * @Author: Cream
 * @Date: 2022-09-24-23:25
 * @Description:
 */
public interface EmployeeService extends IService<EmployeeEntity> {
    EmployeeEntity getEmployeeByUserName(EmployeeEntity entity);


    IPage<EmployeeEntity> getEmployees(Page<EmployeeEntity> p, String name);

}
