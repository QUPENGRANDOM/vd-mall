package com.vd.mall.admin.security.handler;

import com.vd.mall.admin.response.SuccessResponse;
import com.vd.mall.admin.security.UserDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Created by pengq on 2019/9/17 8:54.
 */
@Slf4j
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        if (authentication != null) {
            log.info("用户[{}]于[{}]注销成功!", ((UserDetail) authentication.getPrincipal()).getUsername(), new Date());
        }
       
        WriteResponse.write(httpServletResponse, new SuccessResponse());
    }
}
