package com.imooc.core.validate.code;

import lombok.Data;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * @author suxing.zhang
 * @date 2021/8/31 21:56
 **/
@Data
public class ImageCode {
    private BufferedImage bufferedImage;
    private String code;
    private LocalDateTime expireTime;

    public ImageCode(BufferedImage bufferedImage, String code, int expire) {
        this.bufferedImage = bufferedImage;
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expire);
    }

    public static boolean isExpired(LocalDateTime expireTime){
        return expireTime.isAfter(LocalDateTime.now());
    }

    public boolean isExpired(){
        return this.expireTime.isAfter(LocalDateTime.now());
    }
}
