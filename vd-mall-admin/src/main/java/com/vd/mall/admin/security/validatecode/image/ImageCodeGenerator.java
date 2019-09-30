package com.vd.mall.admin.security.validatecode.image;

import com.vd.mall.admin.security.validatecode.AbstractValidateCodeProcessor;
import com.vd.mall.admin.security.validatecode.ValidateCode;
import com.vd.mall.admin.security.validatecode.ValidateCodeProperties;
import com.vd.mall.admin.security.validatecode.ValidateCodeStorage;

/**
 * Created by pengq on 2019/9/27 13:42.
 */
public class ImageCodeGenerator extends AbstractValidateCodeProcessor {
    private ImageDrawer imageDrawer;

    public ImageCodeGenerator(ValidateCodeStorage validateCodeStorage, ValidateCodeProperties validateCodeProperties, ImageDrawer imageDrawer) {
        super(validateCodeStorage, validateCodeProperties);
        this.imageDrawer = imageDrawer;
    }

    @Override
    public Object send() {
        ValidateCode validateCode = this.generator();
        return imageDrawer.draw(validateCode);
    }
}
