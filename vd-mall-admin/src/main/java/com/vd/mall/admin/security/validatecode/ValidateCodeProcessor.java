package com.vd.mall.admin.security.validatecode;

import com.vd.mall.admin.security.validatecode.exception.ValidateCodeException;

import javax.servlet.http.HttpServletRequest;

public interface ValidateCodeProcessor extends ValidateCodeSender{
    ValidateCode generator();
    void verification(HttpServletRequest request, String code) throws ValidateCodeException;
    boolean store(HttpServletRequest request,ValidateCode code);
}
