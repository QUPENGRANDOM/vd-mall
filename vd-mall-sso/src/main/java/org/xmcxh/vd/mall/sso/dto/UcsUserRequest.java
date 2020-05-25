package org.xmcxh.vd.mall.sso.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.xmcxh.vd.mall.sso.modle.UcsUser;
import org.xmcxh.vd.mall.sso.modle.UcsUserStatus;

/**
 * Created by pengq on 2020/5/12 14:50.
 */
@Data
@ApiModel("用户请求实体")
public class UcsUserRequest {
    @ApiModelProperty("登录名")
    private String username;

    @ApiModelProperty("登录密码")
    private String password;

    @ApiModelProperty("显示名")
    private String nickname;

    @ApiModelProperty("所属地")
    private String address;

    @ApiModelProperty("用户状态 ENABLED-启用 DISABLED-禁用，默认启用")
    private UcsUserStatus status = UcsUserStatus.ENABLED;

    @ApiModelProperty("角色Id")
    private Long roleId;

    public UcsUser transfer() {
        UcsUser ucsUser = new UcsUser();
        ucsUser.setUsername(username);
        ucsUser.setNickname(nickname);
        ucsUser.setPassword(password);
        ucsUser.setAddress(address);
        ucsUser.setStatus(status);
        ucsUser.setRoleId(roleId);
        return ucsUser;
    }
}
