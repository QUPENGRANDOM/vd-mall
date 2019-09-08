package com.vd.mall.admin.service.impl;

import com.vd.mall.admin.entity.User;
import com.vd.mall.admin.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by pengq on 2019/9/7 13:39.
 */
@Service
public class UserServiceImpl implements UserService {
    @Override
    public boolean login(String username, String password) {

        return true;
    }

    @Override
    public List<User> listUser() {
        return null;
    }

    @Override
    public boolean register(User user) {
        return false;
    }
}
