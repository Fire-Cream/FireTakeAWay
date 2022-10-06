package com.cream.fire_takeaway.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cream.fire_takeaway.entity.OrderDetailEntity;
import com.cream.fire_takeaway.mapper.OrderDetailMapper;
import com.cream.fire_takeaway.service.OrderDetailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: Cream
 * @Date: 2022-09-26-14:28
 * @Description:
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetailEntity> implements OrderDetailService {
}
