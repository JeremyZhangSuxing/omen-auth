package com.imooc.core.validate.code.processor;

import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.request.ServletWebRequest;

import java.io.IOException;

/**
 * @author suxing.zhang
 * @date 2021/9/8 22:22
 **/
public interface ValidateCodeProcessor {
    /**
     * 收敛校验逻辑
     *
     * @param servletWebRequest 服务器请求
     * @throws ServletRequestBindingException 异常
     */
    void validate(ServletWebRequest servletWebRequest) throws ServletRequestBindingException;

    /**
     * 生成token流程
     */
    void create(ServletWebRequest request) throws IOException;
}
