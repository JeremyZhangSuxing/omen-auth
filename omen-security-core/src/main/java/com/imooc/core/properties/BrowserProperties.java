package com.imooc.core.properties;

import lombok.Data;

/**
 * @author : Knight
 * @date : 2021/8/31 1:52 下午
 */
@Data
public class BrowserProperties {
    private String loginPage = "/imooc-signIn.html";
    private String sinUpUrl = "/imooc-signUp.html";
    private LoginType loginType = LoginType.JSON;

    private int rememberMe = 3600;

    public enum LoginType {
        JSON, REDIRECT;
    }

}
