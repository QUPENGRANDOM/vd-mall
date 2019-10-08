package com.vd.mall.admin;

import com.vd.mall.admin.security.validatecode.ValidateCodeGeneratorController;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * Created by pengq on 2019/9/7 12:13.
 */
@SpringBootApplication
@MapperScan("com.vd.mall.admin.dao")
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = ValidateCodeGeneratorController.class))
public class VDMallApplication {
    public static void main(String[] args) {
        SpringApplication.run(VDMallApplication.class, args);
    }
}
