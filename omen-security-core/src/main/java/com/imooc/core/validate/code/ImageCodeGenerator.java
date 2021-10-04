package com.imooc.core.validate.code;

import com.imooc.core.properties.SecurityProperties;
import com.imooc.core.validate.code.model.ImageCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.ServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.security.SecureRandom;
import java.util.Random;

/**
 * @author : Knight
 * @date : 2021/9/2 9:54 上午
 */
@Component
@RequiredArgsConstructor
public class ImageCodeGenerator implements ValidateCodeGenerator, InitializingBean {
    private final SecurityProperties securityProperties;
    private Random random;

    @Override
    public ImageCode generate(ServletRequest request) {
        int width = ServletRequestUtils.getIntParameter(request, "width", securityProperties.getImage().getWidth());
        int height = ServletRequestUtils.getIntParameter(request, "length", securityProperties.getImage().getHeight());
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
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
        String sRand = "";
        //验证码的长度
        for (int i = 0; i < securityProperties.getSms().getLength(); i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand += rand;
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            g.drawString(rand, 13 * i + 6, 16);
        }
        g.dispose();
        return new ImageCode(image, sRand, securityProperties.getImage().getExpire());
    }

    /**
     * 生成随机背景条纹
     */
    private Color getRandColor(int fc, int bc) {
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


    @Override
    public void afterPropertiesSet() throws Exception {
        random = SecureRandom.getInstanceStrong();
    }
}
