package com.vd.mall.admin.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Created by pengq on 2019/9/23 17:38.
 */
public class ValidateCodeException extends AuthenticationException {
    public ValidateCodeException(String msg, Throwable t) {
        super(msg, t);
    }

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
