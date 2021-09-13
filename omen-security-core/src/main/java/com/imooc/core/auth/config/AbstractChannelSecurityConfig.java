package com.imooc.core.auth.config;

import com.imooc.core.auth.ImoocAuthenticationFailureHandler;
import com.imooc.core.auth.ImoocAuthenticationSuccessProHandler;
import com.imooc.core.validate.code.SecurityConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author suxing.zhang
 * @date 2021/9/12 13:28
 **/
@RequiredArgsConstructor
public class AbstractChannelSecurityConfig extends WebSecurityConfigurerAdapter {
    private final ImoocAuthenticationSuccessProHandler imoocAuthenticationSuccessProHandler;
    private final ImoocAuthenticationFailureHandler imoocAuthenticationFailureHandler;

    /**
     * 密码表单登录配置抽象
     */
    protected void applyPasswordAuthenticationConfig(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
                .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
                .successHandler(imoocAuthenticationSuccessProHandler)
                .failureHandler(imoocAuthenticationFailureHandler);
    }
}
