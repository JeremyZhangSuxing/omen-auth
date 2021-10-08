package com.imooc.core.validate.code.support;

import com.imooc.core.validate.code.SecurityConstants;
import lombok.Getter;

/**
 * @author suxing.zhang
 * @date 2021/9/8 22:07
 **/
@Getter
public enum ValidateCodeType {
    /**
     * 短信验证码
     */
    SMS {
        @Override
        public String getParamNameOnValidate() {
            return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_SMS;
        }
    },
    /**
     * 图片验证码
     */
    IMAGE {
        @Override
        public String getParamNameOnValidate() {
            return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_IMAGE;
        }
    };

    /**
     * 校验时从请求中获取的参数的名字
     */
    public abstract String getParamNameOnValidate();
}
