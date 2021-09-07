package com.imooc.core.validate.code;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.awt.image.BufferedImage;

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
}
