package com.cream.fire_takeaway.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cream.fire_takeaway.entity.UserEntity;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: Cream
 * @Date: 2022-09-25-14:15
 * @Description:
 */
@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {
}
