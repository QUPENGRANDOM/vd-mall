package org.xmcxh.vd.mall.sso.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xmcxh.vd.mall.sso.dto.UcsMenuRequest;
import org.xmcxh.vd.mall.sso.service.UcsMenuService;
import vd.mall.response.RestResponse;
import vd.mall.response.SuccessResponse;

/**
 * Created by pengq on 2020/6/3 15:10.
 */
@RestController
@RequestMapping("/api/v1/menus")
public class UcsMenuController {
    @Autowired
    UcsMenuService ucsMenuService;

    @ApiOperation("添加菜单")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse addMenu(@RequestBody UcsMenuRequest ucsMenuRequest) {
        ucsMenuService.addMenu(ucsMenuRequest);
        return new SuccessResponse();
    }

    
}
