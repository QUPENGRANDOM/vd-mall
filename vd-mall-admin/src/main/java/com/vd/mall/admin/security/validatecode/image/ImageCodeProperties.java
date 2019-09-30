package com.vd.mall.admin.security.validatecode.image;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by pengq on 2019/9/30 9:32.
 */
@Configuration
@ConfigurationProperties(prefix = "spring.security.code.image")
public class ImageCodeProperties {
    private int height = 20;
    private int width = 50;

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
