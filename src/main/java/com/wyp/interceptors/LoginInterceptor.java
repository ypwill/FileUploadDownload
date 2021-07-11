package com.wyp.interceptors;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author WYP
 * @date 2021-07-11 17:27
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();
        Object user = session.getAttribute("user");
        if(user!=null){
            return true;
        }else{
            response.sendRedirect(request.getContextPath()+"/index");
            return false;
        }
    }
}
