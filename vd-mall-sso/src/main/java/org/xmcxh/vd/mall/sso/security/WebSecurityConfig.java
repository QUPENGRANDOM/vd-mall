package org.xmcxh.vd.mall.sso.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.xmcxh.boot.permission.DynamicSecurityService;
import org.xmcxh.boot.permission.SecurityConfig;
import org.xmcxh.vd.mall.sso.service.UcsUserService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * mall-security模块相关配置
 * Created by macro on 2019/11/9.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends SecurityConfig {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UcsUserService ucsUserService;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> ucsUserService.loadUserDetailsByUserName(username);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests().and().exceptionHandling().authenticationEntryPoint(new RestAuthenticationEntryPoint());
        super.configure(httpSecurity);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 这里不启用 api 权限控制
     * @return
     */
    @Bean
    public DynamicSecurityService dynamicSecurityService() {
        return ConcurrentHashMap::new;
    }
}
