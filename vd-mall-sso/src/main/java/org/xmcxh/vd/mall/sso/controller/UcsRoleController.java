package org.xmcxh.vd.mall.sso.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xmcxh.vd.mall.sso.modle.UcsRole;
import org.xmcxh.vd.mall.sso.repository.UcsRoleRepository;
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
    UcsRoleRepository ucsRoleRepository;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse listRole() {
        List<UcsRole> ucsRoles = ucsRoleRepository.selectList(null);

        return new SuccessResponse().withData(ucsRoles);
    }
}
