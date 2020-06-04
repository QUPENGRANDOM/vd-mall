package org.xmcxh.vd.mall.sso.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import vd.mall.response.ErrorResponse;
import vd.mall.response.GlobalResponseCode;
import vd.mall.response.RestResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * 自定义返回结果：未登录或登录过期
 * Created by pengq on 2018/5/14.
 */
@Slf4j
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Cache-Control", "no-cache");
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json");

        GlobalResponseCode code;

        if (e instanceof BadCredentialsException || e instanceof UsernameNotFoundException) {
            code = GlobalResponseCode.USERNAME_OR_PASSWORD_ERROR;
//            } else if (e instanceof ValidateCodeException) {
//                code = GlobalResponseCode.VALIDATE_CODE_NOT_MATCHED_ERROR;
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
        WriteResponse.write(httpServletResponse, response);
    }
}
