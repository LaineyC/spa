package com.lite.app.framework.exception;

public class PermissionException extends BaseException{

    private static final long serialVersionUID = 1L;

    public PermissionException(){
    }

    public PermissionException(BaseException cause){
        super(cause);
    }

    public PermissionException(String code, String message){
        super(code, message);
    }

    public PermissionException(Error error) {
        super(error.getCode(),error.getMessage());
    }

}
