package com.vd.mall.admin.security.handler;

import com.vd.mall.admin.response.SuccessResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import vd.mall.response.RestResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Created by pengq on 2019/9/17 8:52.
 */
@Slf4j
public class AuthenticationFailureHandlerImpl implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        String username = httpServletRequest.getParameter("username");
        log.info("用户[{}]于[{}]登录失败!", username, new Date());
        RestResponse response = new SuccessResponse().withData(false);
        WriteResponse.write(httpServletResponse, response);
    }
}
