package com.vd.mall.admin.controller;

import com.vd.mall.admin.entity.User;
import com.vd.mall.admin.response.SuccessResponse;
import com.vd.mall.admin.security.UserDetail;
import com.vd.mall.admin.service.UserService;
import com.vd.mall.admin.service.impl.TsetNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import vd.mall.response.RestResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

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
    public RestResponse register(@RequestBody User user) throws ParseException {
        boolean registered = userService.register(user);
        return new SuccessResponse().withData(registered);
    }

    @ApiOperation("用户注册2")
    @GetMapping(value = "/v1/users/register2",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public RestResponse register() throws ParseException, ExecutionException, InterruptedException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        TsetNode.DataBaseNode dataBaseNode = new TsetNode.DataBaseNode("vd_mall_", format.parse("2020-01-11"), format.parse("2020-11-11"));
        dataBaseNode.init();
        List<String> databases = dataBaseNode.queryDataNode(format.parse("2020-01-11"), format.parse("2020-11-11"));
        userService.test(databases);
        userService.test();
        return new SuccessResponse().withData(true);
    }
    @ApiOperation("用户注册3")
    @GetMapping(value = "/v1/users/register3",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public RestResponse register3() throws ParseException, ExecutionException, InterruptedException {

        userService.register(userService.listUser().get(0));
        return new SuccessResponse().withData(true);
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
