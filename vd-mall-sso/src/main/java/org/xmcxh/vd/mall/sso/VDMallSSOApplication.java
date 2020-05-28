package org.xmcxh.vd.mall.sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * Created by pengq on 2020/5/25 12:53.
 */
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class VDMallSSOApplication {
    public static void main(String[] args) {
        SpringApplication.run(VDMallSSOApplication.class, args);
    }
}
