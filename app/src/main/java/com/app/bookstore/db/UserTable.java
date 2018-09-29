package com.app.bookstore.db;

import android.support.annotation.TransitionRes;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by ming on 2018/09/21.
 */

@Table(name = "user")
public class UserTable {

    @Column(name = "id", isId = true, autoGen = true)
    private int id;

    @Column(name = "mobile_phone", property = "UNIQUE")
    private String mobile_phone;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    public UserTable() {
    }

    public UserTable(String mobile_phone, String password) {
        this.mobile_phone = mobile_phone;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMobile_phone() {
        return mobile_phone;
    }

    public void setMobile_phone(String mobile_phone) {
        this.mobile_phone = mobile_phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserTable{" +
                "id=" + id +
                ", mobile_phone='" + mobile_phone + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
