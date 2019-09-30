package com.vd.mall.admin.security.validatecode.digital;

import com.vd.mall.admin.security.validatecode.ValidateCode;

import java.util.Date;

/**
 * Created by pengq on 2019/9/29 15:58.
 */
public class DigitalCode extends ValidateCode {
    public DigitalCode(String validateCode, Date expireTime) {
        super(validateCode, expireTime);
    }
}
