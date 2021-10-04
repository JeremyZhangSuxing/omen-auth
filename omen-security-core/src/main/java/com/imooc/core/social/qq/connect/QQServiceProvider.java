package com.imooc.core.social.qq.connect;

import com.imooc.core.social.qq.api.QQ;
import com.imooc.core.social.qq.api.QQImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

/**
 * serviceProvider 中需要 OauthOperation 直接使用 springSecurity的 OAuth2Template
 * 需要的 ServiceApi 类型 就是用自定义的QQ--> QQImpl
 *
 * @author : Knight
 * @date : 2021/9/23 7:32 上午
 */
public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ> {

    private final String appId;
    /**
     * 流程中的第一步：导向认证服务器的url
     */
    private static final String URL_AUTHORIZE = "https://graph.qq.com/oauth2.0/authorize";

    /**
     * 第四步：申请令牌的url
     */
    private static final String URL_ACCESS_TOKEN = "https://graph.qq.com/oauth2.0/token";

    /**
     * Create a new {@link org.springframework.social.oauth2.OAuth2ServiceProvider}.
     * 因为每个应用的AppId和AppSecret都不一样
     * @param appId appId
     * @param appSecret appSecret
     */
    public QQServiceProvider(String appId, String appSecret) {
        super(new QQOAuth2Template(appId, appSecret, URL_AUTHORIZE, URL_ACCESS_TOKEN));
        this.appId = appId;
    }

    @Override
    public QQ getApi(String accessToken) {
        return new QQImpl(accessToken, appId);
    }
}
