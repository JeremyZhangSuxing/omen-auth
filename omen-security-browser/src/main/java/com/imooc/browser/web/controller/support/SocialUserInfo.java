package com.imooc.browser.web.controller.support;

import lombok.Builder;
import lombok.Data;

/**
 * @author : Knight
 * @date : 2021/10/2 11:04 上午
 */
@Data
@Builder
public class SocialUserInfo {

    private String providerId;

    private String nickname;

    private String headImage;

    private String openId;
}
