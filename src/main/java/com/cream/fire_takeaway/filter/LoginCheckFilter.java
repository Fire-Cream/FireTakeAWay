package com.cream.fire_takeaway.filter;

import com.alibaba.fastjson.JSON;
import com.cream.fire_takeaway.common.BaseContext;
import com.cream.fire_takeaway.common.R;
import com.cream.fire_takeaway.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: Cream
 * @Date: 2022-09-25-14:34
 * @Description:检查用户是否登录
 */
@Slf4j
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
public class LoginCheckFilter implements Filter {

    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        HttpServletResponse res = (HttpServletResponse) response;

        //获取请求路径
        String reqUrl = req.getRequestURI();
        //白名单
        String[] url = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/sendMsg",
                "/user/login"
        };

        if (check(reqUrl, url)) {
            chain.doFilter(req, res);
            return;
        }

        Long id = (Long) req.getSession().getAttribute("employee");

        if (id != null) {
            BaseContext.setCurrentId(id);
            chain.doFilter(req, res);
            return;
        }

        Long user = (Long) req.getSession().getAttribute("user");
        if (user != null) {
            BaseContext.setCurrentId(user);
            chain.doFilter(req, res);
            return;
        }


        res.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
    }

    public boolean check(String url, String[] urls) {
        for (String u : urls) {
            boolean match = PATH_MATCHER.match(u, url);
            if (match) return true;
        }
        return false;
    }
}
