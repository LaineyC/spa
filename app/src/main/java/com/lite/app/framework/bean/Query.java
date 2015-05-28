package com.lite.app.framework.bean;

import com.lite.app.framework.enums.Order;

import java.io.Serializable;

public abstract class Query implements Serializable {

    private static final long serialVersionUID = 1L;

    private int page;

    private int pageSize;

    private Order order;

    private String orderFiled;

    public String getOrderFiled() {
        return orderFiled;
    }

    public void setOrderFiled(String orderFiled) {
        this.orderFiled = orderFiled;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

}

