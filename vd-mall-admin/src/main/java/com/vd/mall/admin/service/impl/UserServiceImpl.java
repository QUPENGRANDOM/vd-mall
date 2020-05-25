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
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

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
        return null;
    }

    @Override
    @Transactional
    public boolean register(User user) throws ParseException {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        TsetNode.DataBaseNode dataBaseNode = new TsetNode.DataBaseNode("vd_mall_", format.parse("2020-01-11"), format.parse("2020-11-11"));
        dataBaseNode.init();
        for (int i=0; i<1000000;i++){
            user.setCreateTime(new Date(1577808000000L));
            user.setUsername(System.currentTimeMillis()+i+"");
            String database = dataBaseNode.queryDataNode(user.getCreateTime());
            System.out.println(database);
            if (database == null) continue;
            user.setDatabase("vd_mall");
            userDao.insert(user);
        }
        return true;
    }
    public void test(List<String> databases) throws InterruptedException, ExecutionException {

        ExecutorService service = Executors.newFixedThreadPool(10);
        List<String> result = new ArrayList<>(100000);
        BlockingQueue<Future<List<String>>> queue = new LinkedBlockingQueue<>();
        log.info(System.currentTimeMillis()+"分库");
        for (String database : databases) {
            Future<List<String>> future = service.submit(() -> {
                //dao 层数据查询

                return userDao.findAll(database);
            });
            queue.add(future);
        }
        int queueSize = queue.size();

        for (int i = 0; i < queueSize; i++) {
            result.addAll(queue.take().get());
        }
        // TODO: 2020/1/2 排序 分页

        //注册bean 不关闭线程池
        log.info(System.currentTimeMillis()+"分库" + result.size());
        service.shutdown();
    }

    public void test() {
        log.info(System.currentTimeMillis()+"单库");
        List<String> userList = userDao.findAll("vd_mall");
        //注册bean 不关闭线程池
        log.info(System.currentTimeMillis()+"单库{}",userList.size());
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
