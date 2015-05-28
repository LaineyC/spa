package com.lite.app.membership.model;

import com.lite.app.framework.bean.Query;

public class MemberQuery extends Query {

    private static final long serialVersionUID = 1L;

    private String name;

    private String phone;

    private String email;

    public MemberQuery(){

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
