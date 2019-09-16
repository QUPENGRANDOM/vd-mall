package com.vd.mall.admin.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vd.mall.admin.response.SuccessResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import vd.mall.response.RestResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * Created by pengq on 2019/9/7 12:46.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@PropertySource("classpath:securityIgnore.properties")
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Value("${security.ignore.resource}")
    private String[] securityIgnoreResource;

    @Value("${security.ignore.api}")
    private String[] securityIgnoreApi;

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
                .and().formLogin().loginProcessingUrl("/admin/api/v1/users/login").permitAll()
                //这里指定的是表单中name="username"的参数作为登录用户名，name="password"的参数作为登录密码
                .usernameParameter("username").passwordParameter("password")
                .successHandler((httpServletRequest, httpServletResponse, authentication) -> {
                    //登录成功后获取当前登录用户
                    UserDetail userDetail = (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                    log.info("用户[{}]于[{}]登录成功!", userDetail.getUser().getUsername(), new Date());
                    RestResponse response = new SuccessResponse().withData(true);
                    this.writeResponse(httpServletResponse, response);
                })
                .failureHandler((httpServletRequest, httpServletResponse, e) -> {
                    String username = httpServletRequest.getParameter("username");
                    log.info("用户[{}]于[{}]登录失败!", username, new Date());
                    RestResponse response = new SuccessResponse().withData(false);
                    this.writeResponse(httpServletResponse, response);
                })
                //这里配置的logoutUrl为登出接口，并设置可匿名访问
                .and().logout().logoutUrl("/admin/api/v1/users/logout").permitAll()
                .logoutSuccessHandler((httpServletRequest, httpServletResponse, authentication) -> {
                    if (authentication != null) {
                        log.info("用户[{}]于[{}]注销成功!", ((UserDetail) authentication.getPrincipal()).getUsername(), new Date());
                    }
                    RestResponse response = new SuccessResponse().withData(true);
                    this.writeResponse(httpServletResponse, response);
                });
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

    private void writeResponse(HttpServletResponse httpServletResponse, RestResponse restResponse) throws IOException {
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        PrintWriter out = httpServletResponse.getWriter();
        out.write(objectMapper.writeValueAsString(restResponse));
        out.flush();
        out.close();
    }
}
