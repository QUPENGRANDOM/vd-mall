package com.vd.mall.admin.security.validatecode.image;

import com.google.code.kaptcha.Producer;
import com.vd.mall.admin.security.validatecode.ValidateCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * Created by pengq on 2019/9/29 17:33.
 */
public class DefaultImageDrawer implements ImageDrawer {
    @Autowired
    Producer producer;
    private static final Logger LOG = LoggerFactory.getLogger(DefaultImageDrawer.class);
    private ImageCodeProperties imageCodeProperties;

    public DefaultImageDrawer(ImageCodeProperties imageCodeProperties) {
        this.imageCodeProperties = imageCodeProperties;
    }

    @Override
    public ImageCode draw(ValidateCode code) {
        return generateValidateCode(code);
    }

    private ImageCode generateValidateCode(ValidateCode validateCode) {
        String text = producer.createText();
        //生成图片验证码
        BufferedImage image = producer.createImage(text);
        byte[] imageBuffer = null;
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()){
            ImageIO.write(image, "JPEG", out);
            imageBuffer = out.toByteArray();
        }catch (IOException e) {
            LOG.error("Draw image failed!");
        }
        return new ImageCode(text, validateCode.getExpireTime() , imageBuffer);
    }
}
