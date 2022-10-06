package com.cream.fire_takeaway.dto;

import com.cream.fire_takeaway.entity.OrderDetailEntity;
import com.cream.fire_takeaway.entity.OrdersEntity;
import lombok.Data;

import java.util.List;

/**
 * @Author: Cream
 * @Date: 2022-09-27-15:08
 * @Description:
 */
@Data
public class OrdersDto extends OrdersEntity {

    private String userName;

    private String phone;

    private String address;

    private String consignee;

    private List<OrderDetailEntity> orderDetails;
}
