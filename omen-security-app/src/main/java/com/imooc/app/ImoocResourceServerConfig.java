package com.imooc.app;

import com.imooc.app.authentocation.ImoocAuthenticationFailureHandler;
import com.imooc.app.authentocation.ImoocAuthenticationSuccessProHandler;
import com.imooc.core.properties.SecurityProperties;
import com.imooc.core.validate.code.SecurityConstants;
import com.imooc.core.validate.code.config.SmsCodeAuthenticationSecurityConfig;
import com.imooc.core.validate.code.config.ValidateCodeSecurityConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * @author : Knight
 * @date : 2021/10/5 9:24 上午
 */
@Configuration
@EnableResourceServer
@RequiredArgsConstructor
public class ImoocResourceServerConfig extends ResourceServerConfigurerAdapter {
    private final ImoocAuthenticationSuccessProHandler imoocAuthenticationSuccessProHandler;
    private final ImoocAuthenticationFailureHandler imoocAuthenticationFailureHandler;
    private final SecurityProperties securityProperties;
    private final SpringSocialConfigurer imoocSocialSecurityConfig;
    private final SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;
    private final ValidateCodeSecurityConfig validateCodeSecurityConfig;


    @Override
    public void configure(HttpSecurity http) throws Exception {

        //form login 提取出来
        http.apply(validateCodeSecurityConfig)
                .and()
                .formLogin()
                .loginPage(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
                .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
                .successHandler(imoocAuthenticationSuccessProHandler)
                .failureHandler(imoocAuthenticationFailureHandler)
                .and()
                .apply(imoocSocialSecurityConfig)
                .and()
                .apply(smsCodeAuthenticationSecurityConfig)
                .and()
                //对请求进行授权
                .authorizeRequests()
                .antMatchers(securityProperties.getBrowser().getLoginPage(),
                        "/authentication/require",
                        "/code/image",
                        "/code/sms").permitAll()
                //认证请求
                .anyRequest()
                //都需要身份认证
                .authenticated()
                .and()
                //跨域配置
                .csrf().disable();
    }
}
