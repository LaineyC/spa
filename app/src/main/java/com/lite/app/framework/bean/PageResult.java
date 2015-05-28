package com.lite.app.framework.bean;

import java.io.Serializable;
import java.util.List;

public class PageResult<E extends Serializable,V extends Serializable> implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<E> result;

    private long total;

    private V userData;

    public PageResult(){

    }

    public PageResult(List<E> result, long total) {
        this.result = result;
        this.total = total;
    }

    public PageResult(List<E> result, long total,V userData) {
        this.result = result;
        this.total = total;
        this.userData = userData;
    }

    public List<E> getResult() {
        return result;
    }

    public void setResult(List<E> result) {
        this.result = result;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public V getUserData() {
        return userData;
    }

    public void setUserData(V userData) {
        this.userData = userData;
    }

}
