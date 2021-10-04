package com.imooc.security;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;

/**
 * @author : Knight
 * @date : 2021/10/2 10:26 上午
 */
@Component
public class QQConnectionSignUp implements ConnectionSignUp {

    @Override
    public String execute(Connection<?> connection) {
        //根据社交用户信息默认创建用户信息 业务系统自己个性化这个实现
        return RandomStringUtils.random(10);
    }
}
