package com.imooc.core.validate.code;

import javax.servlet.ServletRequest;

/**
 * @author : Knight
 * @date : 2021/9/2 9:51 上午
 */
public interface ValidateCodeGenerator {
    ImageCode generate(ServletRequest request);

}
