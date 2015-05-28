package com.lite.app.framework.exception;

public class DaoException extends BaseException{

    private static final long serialVersionUID = 1L;

    public DaoException(){
    }

    public DaoException(BaseException cause){
        super(cause);
    }

    public DaoException(String code, String message){
        super(code, message);
    }

    public DaoException(Error error) {
        super(error.getCode(), error.getMessage());
    }

}
