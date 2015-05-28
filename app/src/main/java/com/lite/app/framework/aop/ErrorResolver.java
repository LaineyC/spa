package com.lite.app.framework.aop;

import com.lite.app.framework.bean.Dto;
import com.lite.app.framework.exception.*;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import com.lite.app.framework.exception.Error;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ErrorResolver implements HandlerExceptionResolver {

    private HttpMessageConverter<Object> dtoHttpMessageConverter;

    public void setDtoHttpMessageConverter(HttpMessageConverter<Object> dtoHttpMessageConverter) {
        this.dtoHttpMessageConverter = dtoHttpMessageConverter;
    }

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) {
        ModelAndView modelAndView = new ModelAndView();
        List<String> errors = new ArrayList<String>();
        if(exception instanceof BaseException){
            BaseException baseException = (BaseException)exception;
            for(Error error : baseException.getErrors()){
                errors.add(error.getMessage());
            }
        }
        else{
            errors.add("程序出现未知异常 -- " + exception.toString() + " -- 尽快联系技术人员！");
        }
        HttpInputMessage inputMessage = new ServletServerHttpRequest(request);
        List<MediaType> acceptedMediaTypes = inputMessage.getHeaders().getAccept();
        for (MediaType acceptedMediaType : acceptedMediaTypes) {
            if (dtoHttpMessageConverter.canWrite(Object.class, acceptedMediaType)) {
                try{
                    dtoHttpMessageConverter.write(new Dto<Object>(errors), acceptedMediaType, new ServletServerHttpResponse(response));
                    throw new RuntimeException(exception);
                }
                catch (IOException ex){
                    throw new RuntimeException(ex);
                }
            }
        }
        return modelAndView;
    }

}
