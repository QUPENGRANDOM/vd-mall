package org.xmcxh.vd.mall.sso.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.xmcxh.vd.mall.sso.modle.StatusType;
import org.xmcxh.vd.mall.sso.modle.UcsRole;

/**
 * Created by pengq on 2020/5/12 14:50.
 */
@Data
@ApiModel("角色请求实体")
public class UcsRoleRequest {

    private String roleName;

    private StatusType status;

    private String description;

    public UcsRole transfer() {
        UcsRole ucsRole = new UcsRole();
        ucsRole.setRoleName(roleName);
        ucsRole.setStatus(status);
        ucsRole.setDescription(description);
        return ucsRole;
    }
}
