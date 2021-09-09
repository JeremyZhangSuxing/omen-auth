package com.imooc.core.validate.code.processor;

import com.imooc.core.validate.code.ValidateCodeController;
import com.imooc.core.validate.code.ValidateCodeException;
import com.imooc.core.validate.code.model.ImageCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author suxing.zhang
 * @date 2021/9/8 22:42
 **/
@Component
public class ImageValidateCodeProcessor implements ValidateCodeProcessor {
    private final SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Override
    public void validate(ServletWebRequest request) throws ServletRequestBindingException {
        //从当前的请求中获取到验证码信息
        ImageCode codeInSession = (ImageCode) sessionStrategy.getAttribute(request, ValidateCodeController.SESSION_KEY);
        String codeRequest = ServletRequestUtils.getStringParameter(request.getRequest(), "imageCode");
        if (StringUtils.isBlank(codeRequest)) {
            throw new ValidateCodeException("验证码不能为空");
        }
        if (null == codeInSession) {
            throw new ValidateCodeException("验证码信息不存在");
        }
        if (codeInSession.isExpired()) {
            throw new ValidateCodeException("验证码已经过期");
        }
        if (!StringUtils.equals(codeRequest, codeInSession.getCode())) {
            throw new ValidateCodeException("输入的验证码不正确");
        }
    }
}
