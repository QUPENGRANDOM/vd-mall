package com.vd.mall.admin.security.validatecode;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import javax.servlet.Filter;

/**
 * Created by pengq on 2019/9/30 13:49.
 */

public class ValidateCodeSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private Filter validateCodeAuthenticationFilter;

    public ValidateCodeSecurityConfig(Filter validateCodeAuthenticationFilter) {
        this.validateCodeAuthenticationFilter = validateCodeAuthenticationFilter;
    }

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        builder.addFilterBefore(validateCodeAuthenticationFilter, AbstractPreAuthenticatedProcessingFilter.class);
    }
}
