package com.vd.mall.admin.security.handler;

import com.vd.mall.admin.response.ErrorResponse;
import com.vd.mall.admin.response.GlobalResponseCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by pengq on 2019/9/20 11:20.
 *
 */
public class AuthenticationEntryPointHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        WriteResponse.write(httpServletResponse,  new ErrorResponse(GlobalResponseCode.ACCESS_FORBIDDEN_ERROR));
    }
}
