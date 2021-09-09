package com.imooc.core.validate.code;

import com.imooc.core.validate.code.model.ValidateCode;

import javax.servlet.ServletRequest;

/**
 * @author : Knight
 * @date : 2021/9/2 9:51 上午
 */
public interface ValidateCodeGenerator {
    ValidateCode generate(ServletRequest request);
}
