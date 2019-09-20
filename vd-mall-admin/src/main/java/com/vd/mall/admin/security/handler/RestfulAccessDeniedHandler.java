package com.vd.mall.admin.security.handler;

import com.vd.mall.admin.response.ErrorResponse;
import com.vd.mall.admin.response.GlobalResponseCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import vd.mall.response.RestResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by pengq on 2019/9/20 11:12.
 */
public class RestfulAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        WriteResponse.write(httpServletResponse, new ErrorResponse(GlobalResponseCode.ACCESS_FORBIDDEN_ERROR));
    }
}
