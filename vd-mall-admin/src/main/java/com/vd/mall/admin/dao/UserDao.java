package com.vd.mall.admin.dao;

import com.vd.mall.admin.entity.User;
import com.vd.mall.admin.security.UserDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by pengq on 2019/9/7 15:39.
 */
@Repository
public interface UserDao {
    UserDetail getUserDetailsByUserName(@Param("username") String username);
    int insert(User user);
    User findOneById(@Param("id") int id);
    int update(User user);
    List<User> findAll();
}
