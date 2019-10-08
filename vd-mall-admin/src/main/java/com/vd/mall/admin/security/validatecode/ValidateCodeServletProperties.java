package com.vd.mall.admin.security.validatecode;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by pengq on 2019/10/8 13:52.
 */
@ConfigurationProperties(prefix = "spring.security.code.servlet")
@Data
public class ValidateCodeServletProperties {
    private String path;
    private boolean enabled;
}
