package org.xmcxh.vd.mall.sso.dto;

import lombok.Data;

/**
 * Created by pengq on 2020/5/12 15:58.
 */
@Data
public class ModifyPasswordRequest {
    private String oldPassword;
    private String password;
    private String confirmPassword;
}
