package com.imooc.core.validate.code;

import com.imooc.core.properties.SecurityProperties;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;

/**
 * @author : Knight
 * @date : 2021/9/3 11:16 上午
 */
@Component("smsCodeGenerator")
@RequiredArgsConstructor
public class SmsCodeGenerator implements ValidateCodeGenerator {
    private final SecurityProperties securityProperties;

    @Override
    public ValidateCode generate(ServletRequest request) {
        String code = RandomStringUtils.randomNumeric(securityProperties.getSms().getLength());
        return new ValidateCode(code, securityProperties.getSms().getExpire());

    }
}
