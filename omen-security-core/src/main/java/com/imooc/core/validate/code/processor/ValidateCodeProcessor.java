package com.imooc.core.validate.code.processor;

import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author suxing.zhang
 * @date 2021/9/8 22:22
 **/
public interface ValidateCodeProcessor {
    /**
     * 收敛校验逻辑
     *
     * @param servletWebRequest 封装请求和响应
     */
    void validate(ServletWebRequest servletWebRequest) throws ServletRequestBindingException;
}
