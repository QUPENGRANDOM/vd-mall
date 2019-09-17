package com.vd.mall.admin.service.impl;

import com.vd.mall.admin.dao.UserDao;
import com.vd.mall.admin.entity.User;
import com.vd.mall.admin.security.UserDetail;
import com.vd.mall.admin.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    public List<User> listUser() {
        return userDao.findAll();
    }

    @Override
    public boolean register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userDao.insert(user) == 1;
    }

    @Override
    public boolean updatePassword(String oldPassword, String newPassword, String confirmPassword) {
        if (!newPassword.equals(confirmPassword)){
            return false;
        }

        User user = this.getLoginUser();
        if (!passwordEncoder.matches(newPassword,user.getPassword())){
            return false;
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setUpdateTime(new Date());
        return userDao.update(user) == 1;
    }

    private User getLoginUser(){
        UserDetail userDetail = (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetail.getUser();
    }
}
