package com.cream.fire_takeaway.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cream.fire_takeaway.entity.ShoppingCartEntity;

import java.util.List;

/**
 * @Author: Cream
 * @Date: 2022-09-26-14:24
 * @Description:
 */
public interface ShoppingCartService extends IService<ShoppingCartEntity> {
    ShoppingCartEntity saveByShoppingCart(ShoppingCartEntity shoppingCart);

    ShoppingCartEntity subByShoppingCart(ShoppingCartEntity shoppingCart);

    void deleteByUid(Long currentId);

    List<ShoppingCartEntity> getShoppingCartByUid(Long currentId);
}
