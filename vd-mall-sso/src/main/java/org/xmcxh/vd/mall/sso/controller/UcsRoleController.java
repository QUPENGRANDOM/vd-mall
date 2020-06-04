package org.xmcxh.vd.mall.sso.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.xmcxh.vd.mall.sso.dto.UcsRoleRequest;
import org.xmcxh.vd.mall.sso.modle.StatusType;
import org.xmcxh.vd.mall.sso.modle.UcsRole;
import org.xmcxh.vd.mall.sso.repository.UcsRoleRepository;
import org.xmcxh.vd.mall.sso.service.UcsRoleService;
import vd.mall.response.PageResponse;
import vd.mall.response.RestResponse;
import vd.mall.response.SuccessResponse;

import java.util.List;

/**
 * Created by pengq on 2020/5/12 17:26.
 */
@RestController
@RequestMapping("/api/v1/roles")
@Api(value = "角色管理", tags = "UcsRoleController")
public class UcsRoleController {
    @Autowired
    UcsRoleService ucsRoleService;

    @ApiOperation("添加角色信息")
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse addRole(@RequestBody UcsRoleRequest ucsRoleRequest) {
        ucsRoleService.addRole(ucsRoleRequest);
        return new SuccessResponse();
    }

    @ApiOperation("查询所有角色信息（启用状态）")
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse listRole() {
        List<UcsRole> ucsRoles = ucsRoleService.listRoleByStatus(StatusType.ENABLED);

        return new SuccessResponse().withData(ucsRoles);
    }

    @ApiOperation("更新角色信息")
    @GetMapping(value = "/{roleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse updateRole(@PathVariable Long roleId,
                                   @RequestBody UcsRoleRequest ucsRoleRequest) {
        ucsRoleService.updateRole(roleId, ucsRoleRequest);

        return new SuccessResponse();
    }

    @ApiOperation("删除角色信息")
    @DeleteMapping(value = "/{roleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse deleteRole(@PathVariable Long roleId) {
        ucsRoleService.deleteRoleById(roleId);
        return new SuccessResponse();
    }

    @ApiOperation("查询所有角色信息")
    @GetMapping(value = "/paging", produces = MediaType.APPLICATION_JSON_VALUE)
    public PageResponse pagingRole(@RequestParam("page") Integer page,
                                   @RequestParam("size") Integer size) {
        return ucsRoleService.pagingRole(page, size);
    }

    @ApiOperation("为角色分配菜单")
    @PostMapping(value = "/{roleId}/menus", consumes = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse addMenus(@PathVariable("roleId") Long roleId,
                                 @RequestBody List<Long> menuIds) {
        ucsRoleService.addMenus(roleId,menuIds);
        return new SuccessResponse();
    }
}
