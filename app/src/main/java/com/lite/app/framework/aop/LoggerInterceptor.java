package com.lite.app.framework.aop;

import com.lite.app.framework.exception.BaseException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

public class LoggerInterceptor extends HandlerInterceptorAdapter {

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) throws Exception {
        if (exception != null) {
            Throwable originalThrowable  = exception.getCause();
            long startTime = (Long)(request.getAttribute("__handleStartTime"));
            System.out.println("运行失败：" + request.getParameter("method"));
            System.out.println("处理时间：" + (new Date().getTime() - startTime) + "ms");
            System.out.println("异常信息：" + originalThrowable.getMessage());
            if(originalThrowable instanceof BaseException){
                //自定义异常不记录堆栈
            }
            else{
                System.out.println("异常堆栈：" + originalThrowable.toString());
            }
        }
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute("__handleStartTime", new Date().getTime());
        return true;
    }

}