package com.vd.mall.admin.security.validatecode;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vd.mall.admin.security.validatecode.filter.ValidateCodeAuthenticationFilter;
import com.vd.mall.admin.security.validatecode.image.DefaultImageDrawer;
import com.vd.mall.admin.security.validatecode.image.ImageCodeGenerator;
import com.vd.mall.admin.security.validatecode.image.ImageCodeProperties;
import com.vd.mall.admin.security.validatecode.image.ImageDrawer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Created by pengq on 2019/9/30 10:26.
 */
@Configuration
@ConditionalOnClass(WebSecurityConfiguration.class)
@EnableConfigurationProperties(ValidateCodeProperties.class)
public class ValidateCodeAutoConfiguration {
    private ValidateCodeProperties validateCodeProperties;

    public ValidateCodeAutoConfiguration(ValidateCodeProperties validateCodeProperties) {
        this.validateCodeProperties = validateCodeProperties;
    }

    @Bean(value = "imageCodeGenerator")
    @ConditionalOnMissingBean(name = "imageCodeGenerator")
    public ValidateCodeProcessor processor(ValidateCodeStorage storage,ImageDrawer imageDrawer){
        return new ImageCodeGenerator(storage,validateCodeProperties,imageDrawer);
    }

    @Bean
    @ConditionalOnMissingBean(ImageDrawer.class)
    public ImageDrawer imageDrawer(){
        ImageCodeProperties properties = validateCodeProperties.getImage();
        return new DefaultImageDrawer(properties);
    }

    @Bean
    @ConditionalOnMissingBean(ValidateCodeStorage.class)
    public ValidateCodeStorage validateCodeStorage(){
        return new ValidateCodeSessionStorage();
    }

    @Bean("validateCodeConfigurerAdapter")
    public SecurityConfigurerAdapter validateCodeConfigurerAdapter(ValidateCodeProcessor validateCodeProcessor, AuthenticationFailureHandler authenticationFailureHandler){
        ValidateCodeAuthenticationFilter filter = new ValidateCodeAuthenticationFilter();
        filter.setAuthenticationFailureHandler(authenticationFailureHandler);
        filter.setAuthorizeRequests(Arrays.asList(validateCodeProperties.getAuthorizeRequests()));
        filter.setValidateCodeProcessor(validateCodeProcessor);

        return new ValidateCodeSecurityConfig(filter);
    }

    @Bean
    @ConditionalOnMissingBean(AuthenticationFailureHandler.class)
    public AuthenticationFailureHandler authenticationFailureHandler(){
        return (request, response, exception) -> {
            ObjectMapper objectMapper = new ObjectMapper();
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            PrintWriter out = response.getWriter();
            out.write(objectMapper.writeValueAsString(exception.getMessage()));
            out.flush();
            out.close();
        };
    }
}
