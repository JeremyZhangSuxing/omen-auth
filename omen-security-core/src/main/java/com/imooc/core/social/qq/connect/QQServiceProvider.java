package com.imooc.core.social.qq.connect;

import com.imooc.core.social.qq.api.QQ;
import com.imooc.core.social.qq.api.QQImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Template;

/**
 *
 * serviceProvider 中需要 OauthOperation 直接使用 springSecurity的 OAuth2Template
 * 需要的 ServiceApi 类型 就是用自定义的QQ--> QQImpl
 * @author : Knight
 * @date : 2021/9/23 7:32 上午
 */
public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ> {

    private String appId;
    private static final String AUTHENTICATION_URL = "";
    private static final String ACCESS_TOKEN_URL = "";

    /**
     * Create a new {@link org.springframework.social.oauth2.OAuth2ServiceProvider}.
     */
    public QQServiceProvider(String appId, String appSecret) {
        super(new OAuth2Template(appId, appSecret, AUTHENTICATION_URL, ACCESS_TOKEN_URL));
    }

    @Override
    public QQ getApi(String accessToken) {
        return new QQImpl(accessToken, appId);
    }
}
