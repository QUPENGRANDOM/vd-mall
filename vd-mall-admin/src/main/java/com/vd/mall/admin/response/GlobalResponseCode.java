package com.vd.mall.admin.response;

import vd.mall.response.IErrorCode;

public enum GlobalResponseCode implements IErrorCode {
    SUCCESS(200, "操作成功！"),

    //账户相关
    USERNAME_OR_PASSWORD_ERROR(501, "用户名或密码不正确！"),
    ACCOUNT_LOCKED_ERROR(502, "账户被锁定！"),
    CREDENTIALS_EXPIRED_ERROR(503, "密码过期！"),
    ACCOUNT_EXPIRED_ERROR(504, "账户过期！"),
    ACCOUNT_DISABLED_ERROR(505, "账户被禁用！"),
    LOGIN_FAILED_ERROR(506, "登录失败！");


    private int code;
    private String message;

    GlobalResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
    }
