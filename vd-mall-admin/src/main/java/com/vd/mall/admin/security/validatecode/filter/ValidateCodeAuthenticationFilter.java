package com.vd.mall.admin.security.validatecode.filter;

import com.vd.mall.admin.security.validatecode.ValidateCodeProcessor;
import com.vd.mall.admin.security.validatecode.exception.ValidateCodeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by pengq on 2019/9/30 11:26.
 */

public class ValidateCodeAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(ValidateCodeAuthenticationFilter.class);
    private static final String SPRING_SECURITY_FORM_VALIDATE_CODE_KEY = "validateCode";
    private ValidateCodeProcessor validateCodeProcessor;
    private AuthenticationFailureHandler authenticationFailureHandler;
    private List<String> authorizeRequests;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (checkMatch(request)) {
            String validateCode = obtainValidateCode(request);
            if (validateCode == null) {
                validateCode = "";
            }

            validateCode = validateCode.trim();

            try {
                validateCodeProcessor.verification(request, validateCode);
            } catch (ValidateCodeException e) {
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
                return;
            }

        }
        filterChain.doFilter(request, response);
    }

    private String obtainValidateCode(HttpServletRequest request) {
        return request.getParameter(SPRING_SECURITY_FORM_VALIDATE_CODE_KEY);
    }

    private boolean checkMatch(HttpServletRequest request) {
        String url = request.getRequestURI();
        log.debug("Request URL is :[{}]", url);
        return authorizeRequests.contains(url);
    }

    public void setValidateCodeProcessor(ValidateCodeProcessor validateCodeProcessor) {
        this.validateCodeProcessor = validateCodeProcessor;
    }

    public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    public void setAuthorizeRequests(List<String> authorizeRequests) {
        this.authorizeRequests = authorizeRequests;
    }
}
