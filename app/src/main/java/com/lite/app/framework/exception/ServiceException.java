package com.lite.app.framework.exception;

public class ServiceException extends BaseException{

    private static final long serialVersionUID = 1L;

    public ServiceException(){
    }

    public ServiceException(BaseException cause){
        super(cause);
    }

    public ServiceException(String code, String message){
        super(code, message);
    }

    public ServiceException(Error error) {
        super(error.getCode(),error.getMessage());
    }

}
