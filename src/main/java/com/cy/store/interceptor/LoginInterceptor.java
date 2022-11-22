package com.cy.store.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** 定义一个拦截器*/
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //获取session对象
        Object obj = request.getSession().getAttribute("uid");
        if (obj == null){
            //如果为空，说明用户没有登录过系统，则重定向为login。html页面
            response.sendRedirect("web/login.html");
            //结束后续的调用
            return false;
        }
        //请求放行
        return true;
    }
}
