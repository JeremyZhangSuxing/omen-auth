package com.imooc.core.validate.code.support;

import com.imooc.core.validate.code.processor.ValidateCodeProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author suxing.zhang
 * @date 2021/9/8 22:19
 **/
@Component
@RequiredArgsConstructor
public class ValidateProcessorHolder {
    private final Map<String, ValidateCodeProcessor> validateCodeProcessorMap;

    public ValidateCodeProcessor findValidateProcessor(ValidateType validateType){
        return validateCodeProcessorMap.get(validateType.);
    }


}
