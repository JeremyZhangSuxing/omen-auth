package com.imooc.core.validate.code;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author : Knight
 * @date : 2021/9/3 1:48 下午
 */
@Slf4j
@Component
public class DefaultSmsCodeSender implements SmsCodeSender {
    @Override
    public void sendCode(String mobile, String code) {
        log.info(">>>>>>>>>>>默认收集验证码发送服务<<<<<<<<<<<<<<");
        log.info(">>>>>>mobile {}  code {}", mobile, code);
    }
}
