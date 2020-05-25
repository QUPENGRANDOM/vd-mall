package org.xmcxh.vd.mall.sso.exception;

import lombok.Getter;

/**
 * Created by pengq on 2020/5/12 15:22.
 */
@Getter
public abstract class GeneralException extends Exception {
    private Integer code;

    public GeneralException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public GeneralException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}
