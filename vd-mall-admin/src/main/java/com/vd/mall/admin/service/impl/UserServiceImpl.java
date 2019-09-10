package com.vd.mall.admin.service.impl;

import com.vd.mall.admin.dao.UserDao;
import com.vd.mall.admin.entity.User;
import com.vd.mall.admin.security.UserDetail;
import com.vd.mall.admin.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by pengq on 2019/9/7 13:39.
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public boolean login(String username, String password) {
        UserDetail userDetail = userDao.getUserDetailsByUserName(username);
        if (userDetail == null) {
            //用戶名不存在
            return false;
        }
        if (!passwordEncoder.matches(password, userDetail.getPassword())) {
            // 密码不正确
            return false;
        }
        return true;
    }

    @Override
    public List<User> listUser() {
        return null;
    }

    @Override
    public boolean register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userDao.insert(user) == 1;
    }
}
