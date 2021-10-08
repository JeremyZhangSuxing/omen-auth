package com.imooc.core.validate.code.processor;

import com.imooc.core.support.ServiceException;
import com.imooc.core.validate.code.SecurityConstants;
import com.imooc.core.validate.code.SmsCodeSender;
import com.imooc.core.validate.code.ValidateCodeGenerator;
import com.imooc.core.validate.code.ValidateCodeRepository;
import com.imooc.core.validate.code.impl.AbstractValidateCodeProcessor;
import com.imooc.core.validate.code.model.ValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

/**
 * @author suxing.zhang
 * @date 2021/9/8 22:29
 **/
@Component
public class SmsValidateCodeProcessor extends AbstractValidateCodeProcessor<ValidateCode> {
    private final SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
    private final SmsCodeSender smsCodeSender;

    @Autowired
    protected SmsValidateCodeProcessor(Map<String, ValidateCodeGenerator<ValidateCode>> validateGenerators,
                                       ValidateCodeRepository validateCodeRepository,
                                       SmsCodeSender smsCodeSender) {
        super(validateGenerators, validateCodeRepository);
        this.smsCodeSender = smsCodeSender;
    }


    @Override
    protected void send(ServletWebRequest request, ValidateCode validateCode) {
        String paramName = SecurityConstants.DEFAULT_PARAMETER_NAME_MOBILE;
        String mobile;
        try {
            mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), paramName);
        } catch (ServletRequestBindingException e) {
            throw new ServiceException("缺少参数 mobile ...");
        }
        smsCodeSender.sendCode(mobile, validateCode.getCode());
    }
}
