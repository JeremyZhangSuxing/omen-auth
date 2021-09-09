package com.imooc.core.validate.code.support;

import com.imooc.core.validate.code.processor.ValidateCodeProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
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

    private final Map<ValidateType, ValidateCodeProcessor> map = new HashMap<>();

    public ValidateCodeProcessor findValidateProcessor(ValidateType validateType) {
        return map.get(validateType);
    }

    @Override
    public void afterPropertiesSet() {
        for (Map.Entry<String, ValidateCodeProcessor> entry : validateCodeProcessorMap.entrySet()) {
            if (entry.getKey().contains(ValidateType.IMAGE.name().toLowerCase())) {
                map.put(ValidateType.IMAGE, entry.getValue());
            }
            if (entry.getKey().contains(ValidateType.SMS.name().toLowerCase())) {
                map.put(ValidateType.SMS, entry.getValue());
            }
        }
        log.info("ValidateProcessorHolder initial processors {} ", map);
    }
}
