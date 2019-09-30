package com.vd.mall.admin.security.handler;

import com.vd.mall.admin.response.ErrorResponse;
import com.vd.mall.admin.response.GlobalResponseCode;
import com.vd.mall.admin.security.exception.ValidateCodeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    private String usernameKey;

    public AuthenticationFailureHandlerImpl(String usernameKey) {
        this.usernameKey = usernameKey;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        GlobalResponseCode code;

        if (e instanceof BadCredentialsException || e instanceof UsernameNotFoundException) {
            code = GlobalResponseCode.USERNAME_OR_PASSWORD_ERROR;
        } else if (e instanceof ValidateCodeException) {
            code = GlobalResponseCode.VALIDATE_CODE_NOT_MATCHED_ERROR;
        } else if (e instanceof LockedException) {
            code = GlobalResponseCode.ACCOUNT_LOCKED_ERROR;
        } else if (e instanceof CredentialsExpiredException) {
            code = GlobalResponseCode.CREDENTIALS_EXPIRED_ERROR;
        } else if (e instanceof AccountExpiredException) {
            code = GlobalResponseCode.ACCOUNT_EXPIRED_ERROR;
        } else if (e instanceof DisabledException) {
            code = GlobalResponseCode.ACCOUNT_DISABLED_ERROR;
        } else {
            code = GlobalResponseCode.LOGIN_FAILED_ERROR;
        }
        RestResponse response = new ErrorResponse(code);
        String username = httpServletRequest.getParameter(usernameKey);
        log.info("用户[{}]于[{}]登录失败,失败原因：[{}]", username, new Date(), response.getMessage());

        WriteResponse.write(httpServletResponse, response);
    }
}
