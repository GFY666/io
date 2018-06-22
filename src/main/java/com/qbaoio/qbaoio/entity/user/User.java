package com.qbaoio.qbaoio.entity.user;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="qbao_user")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User extends BaseEntity {

    @Column(name = "user_name")
    private String userName     ;
    @Column(name = "pass_word")
    private String passWord     ;
    @Column(name = "email")
    private String email        ;
    @Column(name = "activate_type")
    private int activateType ;
    @Column(name = "phone")
    private String phone        ;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getActivateType() {
        return activateType;
    }

    public void setActivateType(int activateType) {
        this.activateType = activateType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
