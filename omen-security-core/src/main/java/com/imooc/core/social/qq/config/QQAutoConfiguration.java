package com.imooc.core.social.qq.config;

import com.imooc.core.properties.QQProperties;
import com.imooc.core.properties.SecurityProperties;
import com.imooc.core.social.qq.connect.QQConnectionFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;

/**
 * @author suxing.zhang
 * @date 2021/9/25 11:35
 **/
@Configuration
@ConditionalOnProperty(prefix = "imooc-security.social.qq", name = "app-id")
@RequiredArgsConstructor
public class QQAutoConfiguration extends SocialAutoConfigurerAdapter {
    private final SecurityProperties securityProperties;

    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        QQProperties qq = securityProperties.getSocial().getQq();
        return new QQConnectionFactory(qq.getProviderId(), qq.getAppId(), qq.getAppSecret());
    }
}
