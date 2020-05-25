package org.xmcxh.vd.mall.sso.exception;

/**
 * Created by pengq on 2020/5/12 15:39.
 */
@ExceptionMarker(value = 1000, description = "用户名已存在", group = "user")
public class UserNameExistsException extends GeneralException {
    private static final Integer code = 1000001;

    public UserNameExistsException(String message) {
        super(code, message);
    }

    public UserNameExistsException(String message, Throwable cause) {
        super(code, message, cause);
    }
}
