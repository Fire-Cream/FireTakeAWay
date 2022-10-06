package com.cream.fire_takeaway.controller;

import com.cream.fire_takeaway.common.R;
import com.cream.fire_takeaway.entity.UserEntity;
import com.cream.fire_takeaway.service.UserService;
import com.cream.fire_takeaway.utils.SentMail;
import com.cream.fire_takeaway.utils.ValidateCodeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * @Author: Cream
 * @Date: 2022-10-02-14:15
 * @Description:
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SentMail sentMail;

    @PostMapping("/sendMsg")
    public R sendMsg(@RequestBody UserEntity userEntity, HttpServletRequest request) {
        //获取邮箱
        String email = userEntity.getEmail();
        if (StringUtils.isNotEmpty(email)) {
            //生成验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            //保存生成的验证码
            request.getSession().setAttribute("code", code);
            try {
                sentMail.sendMail(email, code);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            return R.success(code);
        }
        return R.error("0");
    }

    @PostMapping("/login")
    public R login(@RequestBody Map user, HttpServletRequest request) {
        //获取邮箱跟验证码
        String email = user.get("email").toString();
        String code = user.get("code").toString();
        //获取session的验证码
        String co = request.getSession().getAttribute("code").toString();
        //判断验证码
        //登录成功
        if (co != null && co.equals(code)) {
            //判断用户是否注册
            UserEntity userEntity = userService.getUserByEmail(email);
            //如果用户不存在就直接注册，否则登录
            if (userEntity == null) {
                userEntity = new UserEntity();
                userEntity.setName(email);
                userEntity.setEmail(email);
                userEntity.setStatus(1);
                userService.save(userEntity);
                userEntity = userService.getUserByEmail(email);
            }
            System.out.println("---------------------------" + userEntity.getId());
            request.getSession().setAttribute("user", userEntity.getId());
            return R.success(userEntity);
        }
        return R.error("登录失败");
    }
}
