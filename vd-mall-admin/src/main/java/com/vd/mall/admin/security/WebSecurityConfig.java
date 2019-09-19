package com.vd.mall.admin.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vd.mall.admin.security.handler.AuthenticationFailureHandlerImpl;
import com.vd.mall.admin.security.handler.AuthenticationSuccessHandlerImpl;
import com.vd.mall.admin.security.handler.LogoutSuccessHandlerImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by pengq on 2019/9/7 12:46.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@PropertySource("classpath:security-config.properties")
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Value("${security.ignore.resource}")
    private String[] securityIgnoreResource;

    @Value("${security.ignore.api}")
    private String[] securityIgnoreApi;

    @Value("${security.login.url}")
    private String loginApi;

    @Value("${security.logout.url}")
    private String logoutApi;

    @Value("${security.login.username.key:username}")
    private String usernameKey;

    @Value("${security.login.password.key:password}")
    private String passwordKey;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserDetailService userDetailService;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .authorizeRequests()
                //对于静态资源的获取允许匿名访问
                .antMatchers(HttpMethod.GET, securityIgnoreResource).permitAll()
                // 对登录注册要允许匿名访问;
                .antMatchers(securityIgnoreApi).permitAll()
                //其余请求全部需要登录后访问
                .anyRequest().authenticated()
                //这里配置的loginProcessingUrl为页面中对应表单的 action ，该请求为 post，并设置可匿名访问
                .and().formLogin().loginProcessingUrl(loginApi).permitAll()
                //这里指定的是表单中name="username"的参数作为登录用户名，name="password"的参数作为登录密码
                .usernameParameter(usernameKey).passwordParameter(passwordKey)
                .successHandler(new AuthenticationSuccessHandlerImpl())
                .failureHandler(new AuthenticationFailureHandlerImpl(usernameKey))
                //这里配置的logoutUrl为登出接口，并设置可匿名访问
                .and().logout().logoutUrl(logoutApi).permitAll()
                .logoutSuccessHandler(new LogoutSuccessHandlerImpl());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        //配置密码加密，这里声明成bean，方便注册用户时直接注入
        return new BCryptPasswordEncoder();
    }
}
