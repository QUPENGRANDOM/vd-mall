package com.vd.mall.admin.dao;

import com.vd.mall.admin.security.UserDetail;
import org.springframework.stereotype.Repository;

/**
 * Created by pengq on 2019/9/7 15:39.
 */
@Repository
public interface UserDao {
    UserDetail getUserDetailsByUserName(String username);
}
