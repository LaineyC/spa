package com.lite.app.framework.exception;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class BaseException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    private List<Error> errors;

    public BaseException(){
    }

    public BaseException(BaseException cause){
        super(cause);
        addErrors(cause.getErrors());
    }

    public BaseException(String code, String message){
        super(message);
        addError(code, message);
    }

    public BaseException(Error error) {
        super(error.getMessage());
        addError(error);
    }

    public Throwable getRoot(){
        Throwable cause = getCause();
        while ((cause != null) && (cause.getCause() != null)) {
            cause = cause.getCause();
        }
        return cause;
    }

    public void addError(String code, String message){
        _getErrors().add(new Error(code, message));
    }

    public void addError(Error error) {
        _getErrors().add(error);
    }

    public void addErrors(Collection<Error> errors) {
        for(Error error : errors){
            _getErrors().add(error);
        }
    }

    public void clearErrors(){
        if (this.errors != null)
            this.errors.clear();
    }

    public boolean hasError(){
        return (this.errors != null) && (!this.errors.isEmpty());
    }

    public List<Error> getErrors(){
        return errors;
    }

    public void setErrors(List<Error> errors){
        this.errors = errors;
    }

    private List<Error> _getErrors() {
        if (this.errors == null) {
            this.errors = new ArrayList<Error>();
        }
        return this.errors;
    }

}