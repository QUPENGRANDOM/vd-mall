package com.vd.mall.admin.security.validatecode;

import com.vd.mall.admin.security.validatecode.image.ImageCodeProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by pengq on 2019/9/30 9:11.
 */
@Configuration
@ConfigurationProperties(prefix = "spring.security.code")
public class ValidateCodeProperties {
    private int length;
    private int expireInSecond;
    private String[] authorizeRequests;
    private ImageCodeProperties image = new ImageCodeProperties();

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getExpireInSecond() {
        return expireInSecond;
    }

    public void setExpireInSecond(int expireInSecond) {
        this.expireInSecond = expireInSecond;
    }

    public String[] getAuthorizeRequests() {
        return authorizeRequests;
    }

    public void setAuthorizeRequests(String[] authorizeRequests) {
        this.authorizeRequests = authorizeRequests;
    }

    public ImageCodeProperties getImage() {
        return image;
    }

    public void setImage(ImageCodeProperties image) {
        this.image = image;
    }
}
