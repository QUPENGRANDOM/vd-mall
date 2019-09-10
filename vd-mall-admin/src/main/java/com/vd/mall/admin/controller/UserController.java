package com.vd.mall.admin.controller;

import com.vd.mall.admin.entity.User;
import com.vd.mall.admin.response.SuccessResponse;
import com.vd.mall.admin.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import vd.mall.response.RestResponse;

import java.util.Date;
import java.util.List;

/**
 * Created by pengq on 2019/9/7 12:20.
 */
@Api(value = "/users", tags = "用户管理")
@RestController
@RequestMapping("/admin/api")
@Slf4j
public class UserController {
    @Autowired
    UserService userService;

    @ApiOperation("查询用户列表")
    @GetMapping(value = "/v1/users", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public RestResponse getUsers() {
        List<User> users = userService.listUser();
        return new SuccessResponse().withData(users);
    }

    @ApiOperation("用户注册")
    @PostMapping(value = "/v1/users/register", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public RestResponse register(@RequestBody User user) {
        boolean registered = userService.register(user);
        return new SuccessResponse().withData(registered);
    }

//    @ApiOperation("用户登录")
//    @ApiImplicitParams({
//            @ApiImplicitParam(value = "用户名", name = "username", paramType = "query"),
//            @ApiImplicitParam(value = "登录密码", name = "password", paramType = "query")
//    })
//    @PostMapping(value = "/v1/users/login", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public RestResponse login(@RequestParam(value = "username") String username,
//                              @RequestParam(value = "password") String password) {
//        log.info("[{}] login at [{}]", username, new Date());
//        boolean login = userService.login(username, password);
//        return new SuccessResponse().withData(login);
//    }
}
