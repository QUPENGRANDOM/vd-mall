package com.vd.mall.admin.service;

import com.vd.mall.admin.entity.User;

import java.util.List;

/**
 * Created by pengq on 2019/9/7 13:38.
 */
public interface UserService {
    boolean login(String username,String password);

    List<User> listUser();

    boolean register(User user);
}
