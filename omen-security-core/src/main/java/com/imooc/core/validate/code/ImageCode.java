package com.imooc.core.validate.code;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * @author suxing.zhang
 * @date 2021/8/31 21:56
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class ImageCode extends ValidateCode {
    private BufferedImage bufferedImage;

    public ImageCode(BufferedImage bufferedImage, String code, int expire) {
        super(code, expire);
        this.bufferedImage = bufferedImage;
    }

    public static boolean isExpired(LocalDateTime expireTime) {
        return expireTime.isAfter(LocalDateTime.now());
    }

    public boolean isExpired() {
        return this.expireTime.isBefore(LocalDateTime.now());
    }
}
