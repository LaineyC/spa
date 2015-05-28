package com.lite.app.membership.model;

import com.lite.app.framework.bean.File;
import com.lite.app.framework.bean.Model;
import java.util.Date;

public class Member extends Model{

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private Integer age;

    private Integer levelId;

    private String phone;

    private String email;

    private Date birthDay;

    private Integer point;

    private Boolean isDelete;

    private File headFile;

    public Member(){

    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public File getHeadFile() {
        return headFile;
    }

    public void setHeadFile(File headFile) {
        this.headFile = headFile;
    }

}
