package com.cream.fire_takeaway.exception;

import com.cream.fire_takeaway.common.R;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @Author: Cream
 * @Date: 2022-09-25-15:29
 * @Description:
 */
@ControllerAdvice
@ResponseBody
public class GlobalException {

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R exception(SQLIntegrityConstraintViolationException exception) {

        if (exception.getMessage().contains("Duplicate entry")) {
            String[] split = exception.getMessage().split(" ");
            String msg = split[2] + "已存在";
            return R.error(msg);
        }

        return R.error("未知错误");
    }

    @ExceptionHandler(CustomException.class)
    public R exceptionHandel(RuntimeException exception) {
        System.out.println("-------------------------------------------------");
        return R.error(exception.getMessage());
    }

}
