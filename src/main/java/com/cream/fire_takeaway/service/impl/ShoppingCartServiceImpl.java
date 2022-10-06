package com.cream.fire_takeaway.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cream.fire_takeaway.entity.ShoppingCartEntity;
import com.cream.fire_takeaway.mapper.ShoppingCartMapper;
import com.cream.fire_takeaway.service.ShoppingCartService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Cream
 * @Date: 2022-09-26-14:31
 * @Description:
 */
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCartEntity> implements ShoppingCartService {
    @Override
    public ShoppingCartEntity saveByShoppingCart(ShoppingCartEntity shoppingCart) {
        LambdaQueryWrapper<ShoppingCartEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ShoppingCartEntity::getUserId, shoppingCart.getUserId());
        lambdaQueryWrapper.eq(shoppingCart.getDishId() != null, ShoppingCartEntity::getDishId, shoppingCart.getDishId());
        lambdaQueryWrapper.eq(shoppingCart.getSetmealId() != null, ShoppingCartEntity::getSetmealId, shoppingCart.getSetmealId());
        ShoppingCartEntity shoppingCartEntity = baseMapper.selectOne(lambdaQueryWrapper);
        if (shoppingCartEntity != null && shoppingCartEntity.getDishFlavor() != shoppingCart.getDishFlavor()) {
            Integer number = shoppingCartEntity.getNumber();
            shoppingCartEntity.setNumber(number + 1);
            baseMapper.updateById(shoppingCartEntity);
        } else {
            shoppingCart.setNumber(1);
            baseMapper.insert(shoppingCart);
            shoppingCartEntity = shoppingCart;
        }
        return shoppingCartEntity;
    }

    @Override
    public ShoppingCartEntity subByShoppingCart(ShoppingCartEntity shoppingCart) {
        LambdaQueryWrapper<ShoppingCartEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ShoppingCartEntity::getUserId, shoppingCart.getUserId());
        lambdaQueryWrapper.eq(shoppingCart.getDishId() != null, ShoppingCartEntity::getDishId, shoppingCart.getDishId());
        lambdaQueryWrapper.eq(shoppingCart.getSetmealId() != null, ShoppingCartEntity::getSetmealId, shoppingCart.getSetmealId());
        ShoppingCartEntity shoppingCartEntity = baseMapper.selectOne(lambdaQueryWrapper);
        if (shoppingCartEntity != null && shoppingCartEntity.getDishFlavor() != shoppingCart.getDishFlavor()) {
            Integer number = shoppingCartEntity.getNumber();
            if (number - 1 > 0) {
                shoppingCartEntity.setNumber(number - 1);
                baseMapper.updateById(shoppingCartEntity);
            } else {
                baseMapper.deleteById(shoppingCartEntity);
            }
        }
        return shoppingCartEntity;
    }

    @Override
    public void deleteByUid(Long currentId) {
        LambdaQueryWrapper<ShoppingCartEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ShoppingCartEntity::getUserId, currentId);
        baseMapper.delete(lambdaQueryWrapper);
    }

    @Override
    public List<ShoppingCartEntity> getShoppingCartByUid(Long currentId) {
        LambdaQueryWrapper<ShoppingCartEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ShoppingCartEntity::getUserId, currentId);
        return baseMapper.selectList(lambdaQueryWrapper);
    }
}
