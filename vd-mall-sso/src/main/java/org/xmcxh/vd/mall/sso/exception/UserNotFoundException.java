package org.xmcxh.vd.mall.sso.exception;

/**
 * Created by pengq on 2020/5/12 15:14.
 */
@ExceptionMarker(value = 1000, description = "未找到该用户", group = "user")
public class UserNotFoundException extends GeneralException {
    private static final Integer code = 1000404;

    public UserNotFoundException(String message) {
        super(code, message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(code, message, cause);
    }
}
