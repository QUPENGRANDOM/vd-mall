package com.vd.mall.admin.security.validatecode;

import com.vd.mall.admin.security.validatecode.exception.ValidateCodeException;
import com.vd.mall.admin.security.validatecode.exception.ValidateCodeExpireException;
import com.vd.mall.admin.security.validatecode.exception.ValidateCodeNotFoundException;
import com.vd.mall.admin.security.validatecode.exception.ValidateCodeNotMatchedException;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by pengq on 2019/9/29 16:27.
 */
public abstract class AbstractValidateCodeProcessor implements ValidateCodeProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractValidateCodeProcessor.class);
    private ValidateCodeStorage validateCodeStorage;
    private ValidateCodeProperties validateCodeProperties;

    public AbstractValidateCodeProcessor(ValidateCodeStorage validateCodeStorage, ValidateCodeProperties validateCodeProperties) {
        this.validateCodeStorage = validateCodeStorage;
        this.validateCodeProperties = validateCodeProperties;
    }

    @Override
    public ValidateCode generator() {
        //生成随机字母和数字
        String code = RandomStringUtils.randomAlphanumeric(validateCodeProperties.getLength());
        long currentTime = System.currentTimeMillis();
        //设置验证码过期时间
        ValidateCode validateCode = new ValidateCode(code, new Date(currentTime + validateCodeProperties.getExpireInSecond() * 1000));
        LOG.info("The validate code is generated:[{}]", validateCode.getValidateCode());
        return validateCode;
    }

    @Override
    public void verification(HttpServletRequest request, String code) throws ValidateCodeException {
        //从session中取出验证码，这里的 validateCodeStorage为接口 之后可以扩展为 基于redis的
        ValidateCode validateCode = validateCodeStorage.get(request);
        if (validateCode == null) {
            throw new ValidateCodeNotFoundException("The captcha not found");
        }
        if (validateCode.getExpireTime().before(new Date())) {
            throw new ValidateCodeExpireException("The captcha has expired");
        }

        if (!validateCode.getValidateCode().equalsIgnoreCase(code)){
            throw new ValidateCodeNotMatchedException("The captcha not matched");
        }
    }

    @Override
    public boolean store(HttpServletRequest request, ValidateCode code) {
        //向session写入验证码
        return validateCodeStorage.set(request, code);
    }
}
