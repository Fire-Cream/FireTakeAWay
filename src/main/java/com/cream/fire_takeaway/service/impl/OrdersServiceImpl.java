package com.cream.fire_takeaway.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cream.fire_takeaway.entity.OrdersEntity;
import com.cream.fire_takeaway.mapper.OrdersMapper;
import com.cream.fire_takeaway.service.OrdersService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: Cream
 * @Date: 2022-09-26-14:29
 * @Description:
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, OrdersEntity> implements OrdersService {


}
