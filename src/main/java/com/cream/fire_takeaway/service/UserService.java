package com.cream.fire_takeaway.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cream.fire_takeaway.entity.UserEntity;

/**
 * @Author: Cream
 * @Date: 2022-09-25-14:12
 * @Description:
 */
public interface UserService extends IService<UserEntity> {

    IPage<UserEntity> getUsers(Page<UserEntity> p);

    UserEntity getUserByEmail(String email);
}
