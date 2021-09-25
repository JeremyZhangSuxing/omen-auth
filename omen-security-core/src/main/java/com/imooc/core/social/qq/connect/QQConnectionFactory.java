package com.imooc.core.social.qq.connect;

import com.imooc.core.social.qq.api.QQ;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/**
 * @author suxing.zhang
 * @date 2021/9/25 11:08
 **/
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ> {
    /**
     * Create a {@link OAuth2ConnectionFactory}.
     *
     * @param providerId      the provider id e.g. "facebook"
     * the ServiceProvider model for conducting the authorization flow and obtaining a native service API instance.
     * the ApiAdapter for mapping the provider-specific service API model to the uniform
     *                        {@link org.springframework.social.connect.Connection} interface.
     */
    public QQConnectionFactory(String providerId,String appId, String appSecret) {
        super(providerId, new QQServiceProvider(appId, appSecret), new QQAdapter());
    }
}
