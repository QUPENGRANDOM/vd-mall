package com.vd.mall.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by pengq on 2019/9/7 12:13.
 */
@SpringBootApplication
@MapperScan("com.vd.mall.admin.dao")
public class VDMallApplication {
    public static void main(String[] args) {
        SpringApplication.run(VDMallApplication.class, args);
    }
}
