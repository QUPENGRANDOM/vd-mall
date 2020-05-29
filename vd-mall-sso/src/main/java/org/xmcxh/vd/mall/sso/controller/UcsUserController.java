package org.xmcxh.vd.mall.sso.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.xmcxh.vd.mall.sso.dto.LoginRequest;
import org.xmcxh.vd.mall.sso.exception.GeneralException;
import org.xmcxh.vd.mall.sso.dto.ModifyPasswordRequest;
import org.xmcxh.vd.mall.sso.dto.UcsUserRequest;
import org.xmcxh.vd.mall.sso.modle.StatusType;
import org.xmcxh.vd.mall.sso.service.UcsUserService;
import org.xmcxh.vd.mall.sso.vo.UcsUserVO;
import vd.mall.response.PageResponse;
import vd.mall.response.RestResponse;
import vd.mall.response.SuccessResponse;

/**
 * Created by pengq on 2020/5/12 14:23.
 */
@RestController
@Api(value = "用户管理", tags = "UcsUserController")
@RequestMapping("/api/v1/users")
public class UcsUserController {
    @Autowired
    UcsUserService ucsUserService;

    @ApiOperation(value = "添加用户")
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse addUser(@RequestBody UcsUserRequest ucsUserRequest) {
        ucsUserService.createUser(ucsUserRequest);
        return new SuccessResponse();
    }

    @ApiOperation(value = "登录")
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse login(@RequestBody LoginRequest loginRequest) {
        return new SuccessResponse().withData("afafasfasdasd");
    }

    @ApiOperation(value = "修改用户")
    @PutMapping(value = "/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse modifyUser(@PathVariable("userId") Long userId,
                                   @RequestBody UcsUserRequest ucsUserRequest) throws GeneralException {
        ucsUserService.modifyUser(userId, ucsUserRequest.transfer());
        return new SuccessResponse();
    }

    @ApiOperation(value = "获取用户")
    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse getUser(@PathVariable("userId") Long userId) {
        UcsUserVO res = ucsUserService.getUserAndRoleById(userId);
        return new SuccessResponse().withData(res);
    }

    @ApiOperation(value = "修改用户密码")
    @PutMapping(value = "/{userId}/password", consumes = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse modifyUserPassword(@PathVariable("userId") Long userId,
                                           @RequestBody ModifyPasswordRequest modifyPasswordRequest) throws GeneralException {
        ucsUserService.modifyUserPassword(userId, modifyPasswordRequest);
        return new SuccessResponse();
    }

    @ApiOperation(value = "用户禁用")
    @PutMapping(value = "/{userId}/disabled", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse modifyUserStatusDisabled(@PathVariable("userId") Long userId) {
        ucsUserService.modifyUserStatus(userId, StatusType.DISABLED);
        return new SuccessResponse();
    }

    @ApiOperation(value = "用户启用")
    @PutMapping(value = "/{userId}/enabled", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse modifyUserStatusEnabled(@PathVariable("userId") Long userId) {
        ucsUserService.modifyUserStatus(userId, StatusType.ENABLED);
        return new SuccessResponse();
    }

    @ApiOperation(value = "删除用户")
    @DeleteMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse removeUser(@PathVariable("userId") Long userId) {
        ucsUserService.removeUserById(userId);
        return new SuccessResponse();
    }

    @ApiOperation(value = "用户分页")
    @GetMapping(value = "/paging", produces = MediaType.APPLICATION_JSON_VALUE)
    public PageResponse pagingUser(@RequestParam("page") Integer page,
                                   @RequestParam("size") Integer size,
                                   @RequestParam(value = "username", required = false) String name) {
        return ucsUserService.pagingUser(page, size, name);
    }
}
