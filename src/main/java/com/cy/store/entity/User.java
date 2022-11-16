package com.cy.store.entity;

import lombok.Data;

import java.io.Serializable;


//用户的实体类
@Data
public class User extends BaseEntity implements Serializable {
    private Integer uid;  //'用户id',

    private String username;  //用户名',

    private String password;  //'密码',

    private String salt;  //'盐值',

    private String phone;   //'手机号码',

    private String email;   //'电子邮箱

    private Integer gender;   //性别：0-女，1-男

    private String avatar;   //头像

    private Integer isDelete;    //是否删除：0-未删除，1-已删除

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
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

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
}
