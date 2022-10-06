package com.cream.fire_takeaway.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cream.fire_takeaway.entity.EmployeeEntity;
import com.cream.fire_takeaway.service.EmployeeService;
import com.cream.fire_takeaway.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @Author: Cream
 * @Date: 2022-09-24-23:23
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/employee")
@Transactional
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public R login(HttpServletRequest request, @RequestBody EmployeeEntity entity) {
        EmployeeEntity employee = employeeService.getEmployeeByUserName(entity);
        //1.判断账号是否存在
        if (employee == null) return R.error("该用户不存在");

        //2.判断密码是否正确
        String password = DigestUtils.md5DigestAsHex(entity.getPassword().getBytes());

        if (!employee.getPassword().equals(password)) return R.error("账号或密码错误");

        if (employee.getStatus() == 0) return R.error("账号被禁用");

        request.getSession().setAttribute("employee", employee.getId());

        return R.success(employee);
    }

    @PostMapping("/logout")
    public R logout(HttpServletRequest request) {

        request.getSession().removeAttribute("employee");

        return R.success("退出成功");
    }

    @PostMapping
    public R addEmployeeEntity(HttpServletRequest req, @RequestBody EmployeeEntity entity) {
        EmployeeEntity employee = employeeService.getEmployeeByUserName(entity);

        if (employee != null) {
            return R.error("该用户已存在");
        }

        entity.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

//        entity.setCreateTime(LocalDateTime.now());
//
//        entity.setUpdateTime(LocalDateTime.now());
//
//        Long id = (Long) req.getSession().getAttribute("employee");
//
//        entity.setCreateUser(id);
//
//        entity.setUpdateUser(id);

        employeeService.save(entity);

        return R.success("新增员工成功");
    }

    @PutMapping
    public R changeStatus(HttpServletRequest request, @RequestBody EmployeeEntity entity) {
//        entity.setUpdateUser((Long) request.getSession().getAttribute("employee"));
//        entity.setUpdateTime(LocalDateTime.now());
        employeeService.updateById(entity);
        return R.success("修改成功");
    }

    @GetMapping("/{id}")
    public R getInfo(@PathVariable("id") Long id) {
        EmployeeEntity entity = employeeService.getById(id);
        return R.success(entity);
    }

    @GetMapping("/page")
    public R getEmployeeEntitys(
            @RequestParam("page") Long page,
            @RequestParam("pageSize") Long pageSize,
            @RequestParam(value = "name", required = false) String name
    ) {

        Page<EmployeeEntity> p = new Page<>(page, pageSize);

        IPage<EmployeeEntity> pages = employeeService.getEmployees(p, name);

        return R.success(pages);
    }
}
