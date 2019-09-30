package com.vd.mall.admin.security.validatecode.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vd.mall.admin.security.validatecode.ValidateCodeProcessor;
import com.vd.mall.admin.security.validatecode.exception.ValidateCodeException;
import com.vd.mall.admin.security.validatecode.image.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by pengq on 2019/9/27 15:50.
 */
@RestController
@Configuration
@Slf4j
public class ValidateCodeGeneratorController {
    @Autowired
    ValidateCodeProcessor validateCodeProcessor;

    @GetMapping(value = "/refresh", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getValidateCode(HttpServletRequest request) {
        ImageCode code = (ImageCode) validateCodeProcessor.send();
        validateCodeProcessor.store(request, code);
        return code.getImage();
    }
}
