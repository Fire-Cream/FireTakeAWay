package com.cream.fire_takeaway.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.cream.fire_takeaway.common.BaseContext;
import com.cream.fire_takeaway.common.R;
import com.cream.fire_takeaway.entity.AddressBookEntity;
import com.cream.fire_takeaway.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Cream
 * @Date: 2022-10-02-15:24
 * @Description:
 */
@RestController
@RequestMapping("/addressBook")
public class AddressBookController {
    @Autowired
    private AddressBookService addressBookService;

    /**
     * 新增
     */
    @PostMapping
    public R save(@RequestBody AddressBookEntity addressBook) {
        addressBook.setUserId(BaseContext.getCurrentId());
        System.out.println(BaseContext.getCurrentId() + "------------------------------------");
        addressBookService.save(addressBook);
        return R.success(addressBook);
    }

    /**
     * 设置默认地址
     */
    @PutMapping("default")
    public R setDefault(@RequestBody AddressBookEntity addressBook) {
        LambdaUpdateWrapper<AddressBookEntity> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(AddressBookEntity::getUserId, BaseContext.getCurrentId());
        wrapper.set(AddressBookEntity::getIsDefault, 0);
        //SQL:update address_book set is_default = 0 where user_id = ?
        addressBookService.update(wrapper);

        addressBook.setIsDefault(1);
        //SQL:update address_book set is_default = 1 where id = ?
        addressBookService.updateById(addressBook);
        return R.success(addressBook);
    }

    /**
     * 根据id查询地址
     */
    @GetMapping("/{id}")
    public R get(@PathVariable Long id) {
        AddressBookEntity addressBook = addressBookService.getById(id);
        if (addressBook != null) {
            return R.success(addressBook);
        } else {
            return R.error("没有找到该对象");
        }
    }

    /**
     * 查询默认地址
     */
    @GetMapping("default")
    public R getDefault() {
        LambdaQueryWrapper<AddressBookEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AddressBookEntity::getUserId, BaseContext.getCurrentId());
        queryWrapper.eq(AddressBookEntity::getIsDefault, 1);

        //SQL:select * from address_book where user_id = ? and is_default = 1
        AddressBookEntity addressBook = addressBookService.getOne(queryWrapper);

        if (null == addressBook) {
            return R.error("没有找到该对象");
        } else {
            return R.success(addressBook);
        }
    }

    /**
     * 查询指定用户的全部地址
     */
    @GetMapping("/list")
    public R list(AddressBookEntity addressBook) {
        addressBook.setUserId(BaseContext.getCurrentId());

        //条件构造器
        LambdaQueryWrapper<AddressBookEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(null != addressBook.getUserId(), AddressBookEntity::getUserId, addressBook.getUserId());
        queryWrapper.orderByDesc(AddressBookEntity::getUpdateTime);

        //SQL:select * from address_book where user_id = ? order by update_time desc
        return R.success(addressBookService.list(queryWrapper));
    }
}
