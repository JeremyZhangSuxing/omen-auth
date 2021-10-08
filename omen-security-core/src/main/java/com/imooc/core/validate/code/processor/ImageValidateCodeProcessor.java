package com.imooc.core.validate.code.processor;

import com.imooc.core.validate.code.ValidateCodeGenerator;
import com.imooc.core.validate.code.ValidateCodeRepository;
import com.imooc.core.validate.code.impl.AbstractValidateCodeProcessor;
import com.imooc.core.validate.code.model.ImageCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Map;

/**
 * @author suxing.zhang
 * @date 2021/9/8 22:42
 **/
@Component
public class ImageValidateCodeProcessor extends AbstractValidateCodeProcessor<ImageCode> {

    @Autowired
    protected ImageValidateCodeProcessor(Map<String, ValidateCodeGenerator<ImageCode>> validateGenerators,
                                         ValidateCodeRepository validateCodeRepository) {
        super(validateGenerators, validateCodeRepository);
    }

    /**
     * 发送图形验证码，将其写到响应中
     */
    @Override
    protected void send(ServletWebRequest request, ImageCode imageCode) throws IOException {
        ImageIO.write(imageCode.getBufferedImage(), "JPEG", request.getResponse().getOutputStream());
    }
}
