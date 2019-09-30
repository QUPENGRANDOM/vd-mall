package com.vd.mall.admin.security.validatecode.image;

import com.vd.mall.admin.security.validatecode.ValidateCode;

import java.util.Date;

/**
 * Created by pengq on 2019/9/27 13:40.
 */
public class ImageCode extends ValidateCode {
    private byte[] image;
    public ImageCode(String validateCode, Date expireTime, byte[] image) {
        super(validateCode, expireTime);
        this.image = image;
    }

    public byte[] getImage() {
        return image;
    }
}
