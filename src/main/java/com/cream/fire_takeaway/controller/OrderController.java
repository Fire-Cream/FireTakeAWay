package com.cream.fire_takeaway.controller;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.cream.fire_takeaway.common.BaseContext;
import com.cream.fire_takeaway.common.R;
import com.cream.fire_takeaway.entity.*;
import com.cream.fire_takeaway.exception.CustomException;
import com.cream.fire_takeaway.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @Author: Cream
 * @Date: 2022-10-06-14:39
 * @Description:
 */
@RestController
@RequestMapping("/order")
@Transactional
public class OrderController {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressBookService addressBookService;

    @PostMapping("/submit")
    public R submit(@RequestBody OrdersEntity orders) {
        Long currentId = BaseContext.getCurrentId();
        orders.setUserId(currentId);
        // 查询购物车
        List<ShoppingCartEntity> shoppingCartEntityList = shoppingCartService.getShoppingCartByUid(currentId);
        if (shoppingCartEntityList == null || shoppingCartEntityList.size() == 0) {
            throw new CustomException("购物车为空不能下单");
        }
        // 查询用户信息
        UserEntity user = userService.getById(currentId);
        // 查询地址信息
        Long addressBookId = orders.getAddressBookId();
        AddressBookEntity bookEntity = addressBookService.getById(addressBookId);
        if (bookEntity == null) {
            throw new CustomException("地址有误，不能下单");
        }
        Long orderId = IdWorker.getId();
        AtomicInteger amount = new AtomicInteger(0);

        List<OrderDetailEntity> orderDetailEntities = shoppingCartEntityList.stream().map((item) -> {
            OrderDetailEntity orderDetail = new OrderDetailEntity();
            orderDetail.setOrderId(orderId);
            orderDetail.setNumber(item.getNumber());
            orderDetail.setDishFlavor(item.getDishFlavor());
            orderDetail.setDishId(item.getDishId());
            orderDetail.setSetmealId(item.getSetmealId());
            orderDetail.setName(item.getName());
            orderDetail.setImage(item.getImage());
            orderDetail.setAmount(item.getAmount());
            amount.addAndGet(item.getAmount().multiply(new BigDecimal(item.getNumber())).intValue());
            return orderDetail;
        }).collect(Collectors.toList());


        orders.setId(orderId);
        orders.setOrderTime(LocalDateTime.now());
        orders.setCheckoutTime(LocalDateTime.now());
        orders.setStatus(2);
        orders.setAmount(new BigDecimal(amount.get()));
        orders.setUserId(currentId);
        orders.setNumber(String.valueOf(orderId));
        orders.setUserName(user.getName());
        orders.setConsignee(bookEntity.getConsignee());
        orders.setPhone(bookEntity.getPhone());
        orders.setAddress((bookEntity.getProvinceName() == null ? "" : bookEntity.getProvinceName())
                + (bookEntity.getCityName() == null ? "" : bookEntity.getCityName())
                + (bookEntity.getDistrictName() == null ? "" : bookEntity.getDistrictName()
                + (bookEntity.getDetail() == null ? "" : bookEntity.getDetail()))
        );
        ordersService.save(orders);

        orderDetailService.saveBatch(orderDetailEntities);

        shoppingCartService.deleteByUid(currentId);

        return R.success("下单成功");
    }
}
