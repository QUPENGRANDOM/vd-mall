package com.vd.mall.admin.security.validatecode;

import com.vd.mall.admin.security.validatecode.image.ImageCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by pengq on 2019/9/27 15:50.
 */
@Slf4j
@RestController
@RequestMapping("${spring.security.code.servlet.path:/}")
public class ValidateCodeGeneratorController {
    private ValidateCodeProcessor validateCodeProcessor;

    public ValidateCodeGeneratorController(ValidateCodeProcessor validateCodeProcessor) {
        this.validateCodeProcessor = validateCodeProcessor;
    }

    @GetMapping(value = "/refresh", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getValidateCode(HttpServletRequest request) {
        //生成验证码
        ImageCode code = (ImageCode) validateCodeProcessor.send();
        //将验证码存储到session中
        validateCodeProcessor.store(request, code);
        //返回验证码图片的字节数组给页面
        return code.getImage();
    }
}
