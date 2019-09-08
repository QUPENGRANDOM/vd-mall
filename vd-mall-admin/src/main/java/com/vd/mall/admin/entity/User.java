package com.vd.mall.admin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * Created by pengq on 2019/9/7 12:24.
 */
@Data
public class User {
    private int id;
    private String username;
    private String password;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

    @JsonIgnore
    public void setPassword(String password) {
        this.password = password;
    }
}
