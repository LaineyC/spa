package com.lite.app.framework.aop;

import java.io.IOException;
import java.io.OutputStream;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.lite.app.framework.bean.Dto;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import com.alibaba.fastjson.JSON;

public class DtoHttpMessageConverter extends FastJsonHttpMessageConverter {

    @Override
    protected void writeInternal(Object object, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        OutputStream out = outputMessage.getBody();
        object = object instanceof Dto ? object : new Dto<Object>(object);
        String text = JSON.toJSONString(object,getFeatures());
        byte[] bytes = text.getBytes(getCharset());
        out.write(bytes);
    }

}