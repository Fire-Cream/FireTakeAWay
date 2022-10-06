package com.cream.fire_takeaway.controller;

import com.cream.fire_takeaway.common.BaseContext;
import com.cream.fire_takeaway.common.R;
import com.cream.fire_takeaway.entity.ShoppingCartEntity;
import com.cream.fire_takeaway.service.DishService;
import com.cream.fire_takeaway.service.SetmealService;
import com.cream.fire_takeaway.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Cream
 * @Date: 2022-10-06-12:51
 * @Description:
 */
@RestController
@RequestMapping("/shoppingCart")
@Transactional
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    @GetMapping("/list")
    public R getShoppingCartList() {
        List<ShoppingCartEntity> shoppingCartEntityList = shoppingCartService.list();
        return R.success(shoppingCartEntityList);
    }

    @PostMapping("/add")
    public R addShoppingCart(@RequestBody ShoppingCartEntity shoppingCart) {
        // 设置用户id
        Long currentId = BaseContext.getCurrentId();
        shoppingCart.setUserId(currentId);
        // 查询菜品是否在购物车中
        // 如果存在，数量加一
        // 不存在就加到购物车，输了默认为一
        ShoppingCartEntity shoppingCartEntity = shoppingCartService.saveByShoppingCart(shoppingCart);
        return R.success(shoppingCartEntity);
    }

    @PostMapping("/sub")
    public R subShoppingCart(@RequestBody ShoppingCartEntity shoppingCart) {
        // 设置用户id
        Long currentId = BaseContext.getCurrentId();
        shoppingCart.setUserId(currentId);
        ShoppingCartEntity shoppingCartEntity = shoppingCartService.subByShoppingCart(shoppingCart);
        return R.success(shoppingCartEntity);
    }

    @DeleteMapping("/clean")
    public R cleanShoppingCart() {
        Long currentId = BaseContext.getCurrentId();
        shoppingCartService.deleteByUid(currentId);
        return R.success("");
    }
}
