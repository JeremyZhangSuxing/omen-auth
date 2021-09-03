package com.imooc.core.validate.code;

/**
 * @author : Knight
 * @date : 2021/9/3 1:30 下午
 */

public interface SmsCodeSender {

    void sendCode(String mobile, String code);
}
