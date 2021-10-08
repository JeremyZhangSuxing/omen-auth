package com.imooc.core.validate.code;

import com.imooc.core.properties.SecurityProperties;
import com.imooc.core.validate.code.model.ValidateCode;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;

/**
 * @author : Knight
 * @date : 2021/9/3 11:16 上午
 */
@Component("smsValidateCodeGenerator")
@RequiredArgsConstructor
public class SmsValidateCodeGenerator implements ValidateCodeGenerator<ValidateCode> {
    private final SecurityProperties securityProperties;

    @Override
    public ValidateCode generate(ServletRequest request) {
        String code = RandomStringUtils.randomNumeric(securityProperties.getSms().getLength());
        return new ValidateCode(code, securityProperties.getSms().getExpire());

    }
}
