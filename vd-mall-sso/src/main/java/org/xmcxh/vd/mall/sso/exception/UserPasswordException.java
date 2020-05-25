package org.xmcxh.vd.mall.sso.exception;

/**
 * Created by pengq on 2020/5/12 16:03.
 */
public class UserPasswordException extends GeneralException {
    private static final Integer code = 1000102;

    public UserPasswordException(String message) {
        super(code, message);
    }

    public UserPasswordException(String message, Throwable cause) {
        super(code, message, cause);
    }
}
