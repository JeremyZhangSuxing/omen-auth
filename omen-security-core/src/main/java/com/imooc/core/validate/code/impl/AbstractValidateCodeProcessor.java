package com.imooc.core.validate.code.impl;

import com.imooc.core.validate.code.ValidateCodeException;
import com.imooc.core.validate.code.ValidateCodeGenerator;
import com.imooc.core.validate.code.ValidateCodeRepository;
import com.imooc.core.validate.code.model.ValidateCode;
import com.imooc.core.validate.code.processor.ValidateCodeProcessor;
import com.imooc.core.validate.code.support.ValidateCodeType;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.io.IOException;
import java.util.Map;

/**
 * @author : Knight
 * @date : 2021/10/7 2:17 下午
 */
public abstract class AbstractValidateCodeProcessor<C extends ValidateCode> implements ValidateCodeProcessor {
    private static final String BEAN_NAME_SUFFIX = "ValidateCodeProcessor";

    /**
     * 收集系统中所有的 {@link ValidateCodeGenerator} 接口的实现。
     * <p>
     * 这是Spring开发的常见技巧，叫做定向查找（定向搜索）
     * <p>
     * Spring启动时，会查找容器中所有的ValidateCodeGenerator接口的实现，并把Bean的名字作为key，放到map中
     * 在我们这个系统中，ValidateCodeGenerator接口有两个实现，一个是ImageCodeGenerator，一个是SmsCodeGenerator，系统启动完成后，这个map中就会有2个bean，key分别是bean的名字
     * <p>
     * 生成验证码的时候，会根据请求的不同（有一个type值区分是获取短信验证码还是图片验证码），来获取短信验证码的生成器或者图形验证码的生成器
     */
    private final Map<String, ValidateCodeGenerator<C>> validateGenerators;

    private final ValidateCodeRepository validateCodeRepository;

    @Autowired
    protected AbstractValidateCodeProcessor(Map<String, ValidateCodeGenerator<C>> validateGenerators,
                                            ValidateCodeRepository validateCodeRepository) {
        this.validateGenerators = validateGenerators;
        this.validateCodeRepository = validateCodeRepository;
    }

    /**
     * 生成token流程
     */
    @Override
    public void create(ServletWebRequest request) throws IOException {
        // 生成
        C validateCode = generate(request);
        // 放到session
        save(request, validateCode);
        // 发送
        send(request, validateCode);
    }

    /**
     * 生成校验码
     */
    private C generate(ServletWebRequest request) {
        String type = getValidateCodeType().toString().toLowerCase();
        String generatorName = type + ValidateCodeGenerator.class.getSimpleName();
        ValidateCodeGenerator<C> validateCodeGenerator = validateGenerators.get(generatorName);
        if (validateCodeGenerator == null) {
            throw new ValidateCodeException("验证码生成器" + generatorName + "不存在");
        }
        return validateCodeGenerator.generate(request.getRequest());
    }

    /**
     * 保存校验码
     */
    private void save(ServletWebRequest request, C validateCode) {
        ValidateCode code = new ValidateCode(validateCode.getCode(), validateCode.getExpireTime());
        validateCodeRepository.save(request, code, getValidateCodeType());
    }

    /**
     * 发送校验码，由子类实现
     */
    protected abstract void send(ServletWebRequest request, C validateCode) throws IOException;

    /**
     * 根据请求的url获取校验码的类型
     */
    protected ValidateCodeType getValidateCodeType() {
        String type = StringUtils.substringBefore(getClass().getSimpleName(), BEAN_NAME_SUFFIX);
        return ValidateCodeType.valueOf(type.toUpperCase());
    }

    /**
     * validateCode 校验逻辑
     */
    @Override
    public void validate(ServletWebRequest request) {

        ValidateCodeType codeType = getValidateCodeType();
        ValidateCode codeInSession = validateCodeRepository.get(request, codeType);

        String codeInRequest;
        try {
            codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(),
                    codeType.getParamNameOnValidate());
        } catch (ServletRequestBindingException e) {
            throw new ValidateCodeException("获取验证码的值失败");
        }

        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException(codeType + "验证码的值不能为空");
        }

        if (codeInSession == null) {
            throw new ValidateCodeException(codeType + "验证码不存在");
        }

        if (codeInSession.isExpired()) {
            validateCodeRepository.remove(request, codeType);
            throw new ValidateCodeException(codeType + "验证码已过期");
        }

        if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            throw new ValidateCodeException(codeType + "验证码不匹配");
        }

        validateCodeRepository.remove(request, codeType);

    }
}
