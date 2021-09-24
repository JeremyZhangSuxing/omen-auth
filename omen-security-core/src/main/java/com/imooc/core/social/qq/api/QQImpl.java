package com.imooc.core.social.qq.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import java.io.IOException;

/**
 * @author : Knight
 * @date : 2021/9/22 9:09 下午
 */
@Slf4j
@Data
@EqualsAndHashCode(callSuper = false)
public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {

    private static final String URL_GET_OPENID = "";
    private static final String URL_GET_USERINFO = "";
    private ObjectMapper objectMapper = new ObjectMapper();

    private String appId;
    private String openId;

    @Override
    public QQUserInfo getUserInfo() {
        String userInfo = getRestTemplate().getForObject(URL_GET_USERINFO, String.class);
        log.info("获取到的用户信息 >>>>>> {}", userInfo);
        try {
            return objectMapper.readValue(userInfo, QQUserInfo.class);
        } catch (IOException e) {
            return null;
        }

    }

    public QQImpl(String accessToken, String appId) {
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
        this.appId = appId;
        String url = String.format(URL_GET_OPENID, accessToken);
        String openIdResult = getRestTemplate().getForObject(url, String.class);
        log.info("获取到的openId响应 {}", openIdResult);
        String openIdFromQQ = StringUtils.substringBetween(openIdResult, "\"openid:\":", "\"}");
        log.info("openId >>>>> {}", openId);
        this.openId = openIdFromQQ;
    }
}
