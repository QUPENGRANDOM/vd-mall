package com.vd.mall.admin.response;

import vd.mall.response.IErrorCode;

public enum GlobalResponseCode implements IErrorCode {
    SUCESS(200,"The operation is successful.");

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
