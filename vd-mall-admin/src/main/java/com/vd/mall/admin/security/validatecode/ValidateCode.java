package com.vd.mall.admin.security.validatecode;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by pengq on 2019/9/27 13:32.
 */
public class ValidateCode implements Serializable {
    private String validateCode;
    private Date expireTime;

    public ValidateCode(String validateCode, Date expireTime) {
        this.validateCode = validateCode;
        this.expireTime = expireTime;
    }

    public String getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    @Override
    public String toString() {
        return "ValidateCode{" +
                "validateCode='" + validateCode + '\'' +
                ", expireTime=" + expireTime +
                '}';
    }
}
