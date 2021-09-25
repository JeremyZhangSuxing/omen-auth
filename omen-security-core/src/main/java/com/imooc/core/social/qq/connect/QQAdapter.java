package com.imooc.core.social.qq.connect;

import com.imooc.core.social.qq.api.QQ;
import com.imooc.core.social.qq.api.QQUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * @author suxing.zhang
 * @date 2021/9/25 10:58
 **/
public class QQAdapter implements ApiAdapter<QQ> {
    /**
     * 测试服务提供api是否可用
     */
    @Override
    public boolean test(QQ qq) {
        return true;
    }

    /**
     * 设置基本信息
     */
    @Override
    public void setConnectionValues(QQ qq, ConnectionValues connectionValues) {
        QQUserInfo userInfo = qq.getUserInfo();
        connectionValues.setDisplayName(userInfo.getNickname());
        connectionValues.setImageUrl(userInfo.getFigureurl_qq_1());
        connectionValues.setProfileUrl(null);
        connectionValues.setProviderUserId(userInfo.getOpenId());
    }

    /**
     * 个人主页地址 微博推特会有 qq无n
     */
    @Override
    public UserProfile fetchUserProfile(QQ qq) {
        return null;
    }

    /**
     * 更新状态 微博推特会有 qq无
     */
    @Override
    public void updateStatus(QQ qq, String s) {
        //do nothing
    }
}
