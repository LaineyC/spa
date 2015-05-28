package com.lite.app.framework.aop;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class PermissionInterceptor extends HandlerInterceptorAdapter{

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("执行方法：" + request.getParameter("method"));
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null){
            //throw new PermissionException("no-access", "没有访问权限");
        }
        return true;
    }

}
