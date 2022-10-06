package com.cream.fire_takeaway.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cream.fire_takeaway.entity.AddressBookEntity;
import com.cream.fire_takeaway.mapper.AddressBookMapper;
import com.cream.fire_takeaway.service.AddressBookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: Cream
 * @Date: 2022-09-26-14:24
 * @Description:
 */
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBookEntity> implements AddressBookService {
}
