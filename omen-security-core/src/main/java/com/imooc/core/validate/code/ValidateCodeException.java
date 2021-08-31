package com.imooc.core.validate.code;

import org.springframework.security.core.AuthenticationException;

/**
 * @author suxing.zhang
 * @date 2021/8/31 22:28
 **/
public class ValidateCodeException extends AuthenticationException {

    public ValidateCodeException(String message) {
        super(message);
    }
}
