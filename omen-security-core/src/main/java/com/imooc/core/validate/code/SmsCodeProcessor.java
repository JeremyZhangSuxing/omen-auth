package com.imooc.core.validate.code;

import com.imooc.core.validate.code.model.ValidateCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author : Knight
 * @date : 2021/9/3 1:48 下午
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SmsCodeProcessor {
    private final SmsCodeSender smsCodeSender;

    public void send(ServletWebRequest request, ValidateCode validateCode) {
        try {
            String paramName = SecurityConstants.DEFAULT_PARAMETER_NAME_MOBILE;
            String mobile = ServletRequestUtils.getStringParameter(request.getRequest(), paramName);
            smsCodeSender.sendCode(mobile, validateCode.getCode());
        } catch (ServletRequestBindingException e) {
            log.error("手机号码获取失败", e);
            throw new InternalAuthenticationServiceException("短信验证码发送失败");
        }

    }
}
