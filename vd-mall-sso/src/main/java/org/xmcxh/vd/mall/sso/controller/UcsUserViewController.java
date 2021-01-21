package org.xmcxh.vd.mall.sso.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>
 * 系统用户表 前端控制器
 * </p>
 *
 * @author 王梦舒
 * @since 2020-10-13
 */
@Api(hidden = true)
@Controller
@RequestMapping("/view/user")
public class UcsUserViewController {

    @ApiOperation("用户分页查询页面")
    @GetMapping(value = "/list")
    public String getUsersPage(ModelMap modelMap) {
        return "/views/user/user_list";
    }

    @ApiOperation("角色分页查询页面")
    @GetMapping(value = "/roles")
    public String getRolesPage() {
        return "/view/role/role_list";
    }
}
