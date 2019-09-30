package com.vd.mall.admin.security.validatecode.digital;

import com.vd.mall.admin.security.validatecode.AbstractValidateCodeProcessor;
import com.vd.mall.admin.security.validatecode.ValidateCode;
import com.vd.mall.admin.security.validatecode.ValidateCodeProperties;
import com.vd.mall.admin.security.validatecode.ValidateCodeStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by pengq on 2019/9/29 15:59.
 */
public class DigitalCodeGenerator extends AbstractValidateCodeProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(DigitalCodeGenerator.class);

    public DigitalCodeGenerator(ValidateCodeStorage validateCodeStorage, ValidateCodeProperties validateCodeProperties) {
        super(validateCodeStorage, validateCodeProperties);
    }

    @Override
    public Object send() {
        LOG.info("send the code to mail :[{}]" ,"");
        return null;
    }
}
