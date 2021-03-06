package com.imooc.browser.validate.code.impl;

import com.imooc.core.validate.code.ValidateCodeRepository;
import com.imooc.core.validate.code.model.ValidateCode;
import com.imooc.core.validate.code.support.ValidateCodeType;
import lombok.RequiredArgsConstructor;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author : Knight
 * @date : 2021/10/7 10:09 上午
 */
@Component
@RequiredArgsConstructor
public class SessionValidateCodeRepository implements ValidateCodeRepository {
    private static final String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";
    private final SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Override
    public void save(ServletWebRequest request, ValidateCode code, ValidateCodeType validateCodeType) {
        sessionStrategy.setAttribute(request, getSessionKey(validateCodeType), code);
    }

    @Override
    public ValidateCode get(ServletWebRequest request, ValidateCodeType validateCodeType) {
        return (ValidateCode) sessionStrategy.getAttribute(request, getSessionKey(validateCodeType));
    }

    @Override
    public void remove(ServletWebRequest request, ValidateCodeType codeType) {
        sessionStrategy.removeAttribute(request, getSessionKey(codeType));
    }

    /**
     * 构建验证码放入session时的key
     */
    private String getSessionKey(ValidateCodeType validateCodeType) {
        return SESSION_KEY_PREFIX + validateCodeType.toString().toUpperCase();
    }

}
