package com.vd.mall.admin.entity;

import lombok.Data;

import java.util.Date;

/**
 * Created by pengq on 2019/9/7 12:24.
 */
@Data
public class User {
    private int id;
    private String username;
    private String password;
    private String nickname;
    private String avatar;
    private int sex;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private Date updateTime;
    private Date createTime;

//    @JsonIgnore
//    public void setPassword(String password) {
//        this.password = password;
//    }
}
