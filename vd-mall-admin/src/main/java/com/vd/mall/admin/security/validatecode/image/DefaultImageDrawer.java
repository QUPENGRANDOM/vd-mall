package com.vd.mall.admin.security.validatecode.image;

import com.vd.mall.admin.security.validatecode.ValidateCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        int width = imageCodeProperties.getWidth();
        int height = imageCodeProperties.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics g = image.getGraphics();

        Random random = new Random();

        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        g.setColor(getRandColor(160, 200));
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }
        String code = validateCode.getValidateCode();
        StringBuilder sRand = new StringBuilder();
        for (int i = 0; i < code.length(); i++) {
            String rand = String.valueOf(code.charAt(i));
            sRand.append(rand);
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            g.drawString(rand, 13 * i + 6, 16);
        }

        g.dispose();
        byte[] imageBuffer = null;
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()){
            ImageIO.write(image, "PNG", out);
            imageBuffer = out.toByteArray();
        }catch (IOException e) {
            LOG.error("Draw image failed!");
        }
        return new ImageCode(sRand.toString(), validateCode.getExpireTime() , imageBuffer);
    }

    private Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
}
