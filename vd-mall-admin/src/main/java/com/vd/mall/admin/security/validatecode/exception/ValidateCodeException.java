package com.vd.mall.admin.security.validatecode.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Created by pengq on 2019/9/29 17:09.
 */
public class ValidateCodeException extends AuthenticationException {
    public ValidateCodeException(String message) {
        super(message);
    }
}
