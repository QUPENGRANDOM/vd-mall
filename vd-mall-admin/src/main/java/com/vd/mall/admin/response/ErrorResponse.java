package com.vd.mall.admin.response;

import vd.mall.response.GenericResponse;

/**
 * Created by pengq on 2019/9/17 9:09.
 */
public class ErrorResponse extends GenericResponse {
    public ErrorResponse(GlobalResponseCode globalResponseCode) {
        super(globalResponseCode.getCode(), globalResponseCode.getMessage());
    }
}
