package com.vd.mall.admin.service;

import com.vd.mall.admin.entity.User;

import java.text.ParseException;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by pengq on 2019/9/7 13:38.
 */
public interface UserService {
    List<User> listUser();

    boolean register(User user) throws ParseException;
    void test(List<String> databases) throws InterruptedException, ExecutionException;
    void test();
    boolean updatePassword(String oldPassword, String newPassword, String confirmPassword);
}
