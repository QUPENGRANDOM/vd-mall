package com.vd.mall.admin.security;

import com.vd.mall.admin.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by pengq on 2019/9/7 15:36.
 */
@Service
public class UserDetailService implements UserDetailsService {
    @Autowired
    UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetail userDetail = userDao.getUserDetailsByUserName(username);
        if (userDetail == null) {
            throw new UsernameNotFoundException("not found username:" + username);
        }
        return userDetail;
    }
}
