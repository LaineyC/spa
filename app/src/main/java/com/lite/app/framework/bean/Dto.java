package com.lite.app.framework.bean;

import java.io.Serializable;
import java.util.List;

public class Dto<E> implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<String> errors;

    private E data;

    public Dto(){

    }

    public Dto(E data){
        this.data = data;
    }

    public Dto(List<String> errors) {
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

}
