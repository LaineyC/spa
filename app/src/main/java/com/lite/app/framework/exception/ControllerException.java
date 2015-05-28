package com.lite.app.framework.exception;

public class ControllerException extends BaseException{

    private static final long serialVersionUID = 1L;

    public ControllerException(){
    }

    public ControllerException(BaseException cause){
        super(cause);
    }

    public ControllerException(String code, String message){
        super(code, message);
    }

    public ControllerException(Error error) {
        super(error.getCode(),error.getMessage());
    }

}
