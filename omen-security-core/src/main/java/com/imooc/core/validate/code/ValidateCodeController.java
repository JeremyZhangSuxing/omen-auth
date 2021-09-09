package com.imooc.core.validate.code;

import com.imooc.core.validate.code.model.ImageCode;
import com.imooc.core.validate.code.model.ValidateCode;
import lombok.RequiredArgsConstructor;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author suxing.zhang
 * @date 2021/8/31 21:59
 **/
@RestController
@RequiredArgsConstructor
public class ValidateCodeController {
    private final SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
    public static final String SESSION_KEY = "IMAGE_CODE_SESSION_";
    private final ImageCodeGenerator imageCodeGenerator;
    private final ImageCodeGenerator smsCodeGenerator;
    private final SmsCodeProcessor smsCodeProcessor;

    @GetMapping("/code/image")
    public void createImageCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //生成的imageCode 放入session中
        ImageCode imageCode = imageCodeGenerator.generate(request);
        sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY + "IMAGE", imageCode);
        ImageIO.write(imageCode.getBufferedImage(), "JPEG", response.getOutputStream());
    }

    @GetMapping("/code/sms")
    public void createMobileCode(HttpServletRequest request, HttpServletResponse response) {
        //生成的imageCode 放入session中
        ValidateCode validateCode = smsCodeGenerator.generate(request);
        sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY + "SMS", validateCode);
        smsCodeProcessor.send(new ServletWebRequest(request, response), validateCode);
    }
}
