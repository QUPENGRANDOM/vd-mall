package com.vd.mall.admin.security.validatecode.image;

import com.vd.mall.admin.security.validatecode.ValidateCode;

public interface ImageDrawer {
    Object draw(ValidateCode code);
}
