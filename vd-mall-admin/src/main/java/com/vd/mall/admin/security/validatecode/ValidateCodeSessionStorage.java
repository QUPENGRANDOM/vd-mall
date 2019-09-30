package com.vd.mall.admin.security.validatecode;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by pengq on 2019/9/30 9:57.
 */
public class ValidateCodeSessionStorage implements ValidateCodeStorage {
    private static final String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE";

    @Override
    public boolean set(HttpServletRequest request, ValidateCode validateCode) {
        request.getSession().setAttribute(SESSION_KEY_PREFIX, validateCode);
        return true;
    }

    @Override
    public ValidateCode get(HttpServletRequest request) {
        return (ValidateCode) request.getSession().getAttribute(SESSION_KEY_PREFIX);
    }

    @Override
    public boolean remove(HttpServletRequest request) {
        request.getSession().removeAttribute(SESSION_KEY_PREFIX);
        return true;
    }
}
