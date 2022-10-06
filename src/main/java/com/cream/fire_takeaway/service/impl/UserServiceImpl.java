package com.cream.fire_takeaway.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cream.fire_takeaway.entity.UserEntity;
import com.cream.fire_takeaway.mapper.UserMapper;
import com.cream.fire_takeaway.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: Cream
 * @Date: 2022-09-25-14:13
 * @Description:
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

    @Override
    public IPage<UserEntity> getUsers(Page<UserEntity> page) {

        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();

        return baseMapper.selectPage(page, queryWrapper);
    }

    @Override
    public UserEntity getUserByEmail(String email) {
        LambdaQueryWrapper<UserEntity> lambdaQueryWrapper = new LambdaQueryWrapper<UserEntity>();
        lambdaQueryWrapper.eq(UserEntity::getEmail, email);
        return baseMapper.selectOne(lambdaQueryWrapper);
    }
}
