package com.vd.mall.admin.security.validatecode;

import javax.servlet.http.HttpServletRequest;

public interface ValidateCodeStorage {
    boolean set(HttpServletRequest request, ValidateCode validateCode);
    ValidateCode get(HttpServletRequest request);
    boolean remove(HttpServletRequest request);
}
