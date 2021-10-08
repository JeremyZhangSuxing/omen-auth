package com.imooc.core.validate.code.support;

import com.imooc.core.validate.code.processor.ValidateCodeProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

/**
 * @author suxing.zhang
 * @date 2021/9/8 22:19
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class ValidateProcessorHolder implements InitializingBean {
    private final Map<String, ValidateCodeProcessor> validateCodeProcessorMap;

    private final Map<ValidateCodeType, ValidateCodeProcessor> map = new EnumMap<>(ValidateCodeType.class);

    public ValidateCodeProcessor findValidateProcessor(ValidateCodeType validateType) {
        return map.get(validateType);
    }

    public ValidateCodeProcessor findValidateProcessorByType(String type) {
        ValidateCodeType validateCodeType = ValidateCodeType.valueOf(StringUtils.upperCase(type));
        return findValidateProcessor(validateCodeType);
    }

    @Override
    public void afterPropertiesSet() {
        for (Map.Entry<String, ValidateCodeProcessor> entry : validateCodeProcessorMap.entrySet()) {
            if (entry.getKey().contains(ValidateCodeType.IMAGE.name().toLowerCase())) {
                map.put(ValidateCodeType.IMAGE, entry.getValue());
            }
            if (entry.getKey().contains(ValidateCodeType.SMS.name().toLowerCase())) {
                map.put(ValidateCodeType.SMS, entry.getValue());
            }
        }
        log.info("ValidateProcessorHolder initial processors {} ", map);
    }
}
