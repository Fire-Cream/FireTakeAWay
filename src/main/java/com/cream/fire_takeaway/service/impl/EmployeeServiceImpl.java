package com.cream.fire_takeaway.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cream.fire_takeaway.entity.EmployeeEntity;
import com.cream.fire_takeaway.mapper.EmployeeMapper;
import com.cream.fire_takeaway.service.EmployeeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: Cream
 * @Date: 2022-09-24-23:25
 * @Description:
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, EmployeeEntity> implements EmployeeService {
    @Override
    public EmployeeEntity getEmployeeByUserName(EmployeeEntity entity) {

        LambdaQueryWrapper<EmployeeEntity> queryWrapper = new LambdaQueryWrapper<EmployeeEntity>();

        queryWrapper.eq(EmployeeEntity::getUsername, entity.getUsername());

        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public IPage<EmployeeEntity> getEmployees(Page<EmployeeEntity> p, String name) {
        LambdaQueryWrapper<EmployeeEntity> queryWrapper = new LambdaQueryWrapper<EmployeeEntity>();
        queryWrapper.like(name != null, EmployeeEntity::getName, name);
        return baseMapper.selectPage(p, queryWrapper);
    }


}
