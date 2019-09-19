package com.vd.mall.admin.controller;

import com.vd.mall.admin.entity.User;
import com.vd.mall.admin.response.SuccessResponse;
import com.vd.mall.admin.security.UserDetail;
import com.vd.mall.admin.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import vd.mall.response.RestResponse;

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

    @ApiOperation("获取当前登录用户")
    @GetMapping(value = "/v1/users/current", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public RestResponse getCurrentLoginUser() {
        UserDetail userDetail = (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new SuccessResponse().withData(userDetail.getUser());
    }

    @ApiOperation("修改密码")
    @PatchMapping(value = "/v1/users/password", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public RestResponse updatePassword(@RequestParam(name = "oldPassword") String oldPassword,
                                       @RequestParam(name = "newPassword") String newPassword,
                                       @RequestParam(name = "confirmPassword") String confirmPassword) {
        boolean succeeded = userService.updatePassword(oldPassword, newPassword, confirmPassword);
        return new SuccessResponse().withData(succeeded);
    }
}
