package org.xmcxh.vd.mall.sso.validate;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.validation.beanvalidation.OptionalValidatorFactoryBean;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * Created by pengq on 2020/6/5 8:02.
 */
@Configuration
public class ValidateConfig {
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor(){
        MethodValidationPostProcessor postProcessor = new MethodValidationPostProcessor();
        postProcessor.setValidator(validator());

        return postProcessor;
    }

    @Bean
    public Validator validator(){
        return new OptionalValidatorFactoryBean();
    }
}
