package com.lite.app.framework.exception;

import java.io.Serializable;

public class Error implements Serializable {

    private static final long serialVersionUID = 1L;

    private String code;

    private String message;

    public Error(){

    }

    public Error(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
