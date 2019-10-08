package com.vd.mall.admin.security.validatecode;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vd.mall.admin.security.validatecode.filter.ValidateCodeAuthenticationFilter;
import com.vd.mall.admin.security.validatecode.image.DefaultImageDrawer;
import com.vd.mall.admin.security.validatecode.image.ImageCodeGenerator;
import com.vd.mall.admin.security.validatecode.image.ImageCodeProperties;
import com.vd.mall.admin.security.validatecode.image.ImageDrawer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
@EnableConfigurationProperties({ValidateCodeProperties.class, ValidateCodeServletProperties.class})
public class ValidateCodeAutoConfiguration {
    private ValidateCodeProperties validateCodeProperties;

    public ValidateCodeAutoConfiguration(ValidateCodeProperties validateCodeProperties) {
        this.validateCodeProperties = validateCodeProperties;
    }

    @Bean(value = "imageCodeGenerator")
    @ConditionalOnMissingBean(name = "imageCodeGenerator")
    public ValidateCodeProcessor processor(ValidateCodeStorage storage, ImageDrawer imageDrawer) {
        return new ImageCodeGenerator(storage, validateCodeProperties, imageDrawer);
    }

    /**
     * 发布图片验证码获取controller
     * 利用这种发布方式而不选择默认扫描是为了方便后续将Spring Security 模块拆分成单独jar包
     * 由于目前还处于同一包下，所以我们用注解 @ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = ValidateCodeGeneratorController.class))
     * 排除 Spring 自动 扫描注册bean
     * @see  com.vd.mall.admin.VDMallApplication
     * @return ValidateCodeGeneratorController
     */
    @Bean
    @ConditionalOnProperty(prefix = "spring.security.code.servlet", name = "enabled", matchIfMissing = true)
    public ValidateCodeGeneratorController validateCodeGeneratorController(@Qualifier(value = "imageCodeGenerator") ValidateCodeProcessor validateCodeProcessor) {
        return new ValidateCodeGeneratorController(validateCodeProcessor);
    }

    @Bean
    @ConditionalOnMissingBean(ImageDrawer.class)
    public ImageDrawer imageDrawer() {
        ImageCodeProperties properties = validateCodeProperties.getImage();
        return new DefaultImageDrawer(properties);
    }

    @Bean
    @ConditionalOnMissingBean(ValidateCodeStorage.class)
    public ValidateCodeStorage validateCodeStorage() {
        return new ValidateCodeSessionStorage();
    }

    @Bean("validateCodeConfigurerAdapter")
    public SecurityConfigurerAdapter validateCodeConfigurerAdapter(ValidateCodeProcessor validateCodeProcessor, AuthenticationFailureHandler authenticationFailureHandler) {
        ValidateCodeAuthenticationFilter filter = new ValidateCodeAuthenticationFilter();
        filter.setAuthenticationFailureHandler(authenticationFailureHandler);
        filter.setAuthorizeRequests(Arrays.asList(validateCodeProperties.getAuthorizeRequests()));
        filter.setValidateCodeProcessor(validateCodeProcessor);

        return new ValidateCodeSecurityConfig(filter);
    }

    @Bean
    @ConditionalOnMissingBean(AuthenticationFailureHandler.class)
    public AuthenticationFailureHandler authenticationFailureHandler() {
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
