package com.imooc.browser;


import com.imooc.core.auth.ImoocAuthenticationFailureHandler;
import com.imooc.core.auth.ImoocAuthenticationSuccessProHandler;
import com.imooc.core.auth.config.AbstractChannelSecurityConfig;
import com.imooc.core.properties.SecurityProperties;
import com.imooc.core.validate.code.ImageCodeGenerator;
import com.imooc.core.validate.code.config.SmsCodeAuthenticationSecurityConfig;
import com.imooc.core.validate.code.config.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * @author : Knight
 * @date : 2021/8/30 9:55 上午
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class BrowserSecurityConfig extends AbstractChannelSecurityConfig {

    private final SecurityProperties securityProperties;
    private final DataSource dataSource;
    private final UserDetailsService userDetailsService;
    private final SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;
    private final ValidateCodeSecurityConfig validateCodeSecurityConfig;
    private final SpringSocialConfigurer imoocSocialSecurityConfig;

    @Autowired
    public BrowserSecurityConfig(SecurityProperties securityProperties,
                                 ImoocAuthenticationSuccessProHandler imoocAuthenticationSuccessProHandler,
                                 ImoocAuthenticationFailureHandler imoocAuthenticationFailureHandler,
                                 @Qualifier("dataSource") DataSource dataSource,
                                 UserDetailsService userDetailsService,
                                 SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig,
                                 ValidateCodeSecurityConfig validateCodeSecurityConfig,
                                 SpringSocialConfigurer imoocSocialSecurityConfig) {
        super(imoocAuthenticationSuccessProHandler, imoocAuthenticationFailureHandler);
        this.securityProperties = securityProperties;
        this.dataSource = dataSource;
        this.userDetailsService = userDetailsService;
        this.smsCodeAuthenticationSecurityConfig = smsCodeAuthenticationSecurityConfig;
        this.validateCodeSecurityConfig = validateCodeSecurityConfig;
        this.imoocSocialSecurityConfig = imoocSocialSecurityConfig;
    }

    @Bean
    @ConditionalOnMissingBean(value = ImageCodeGenerator.class)
    public ImageCodeGenerator imageCodeGenerator() {
        return new ImageCodeGenerator(securityProperties);
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        //启动的时候就初始化表，注意，就在第一次启动的时候执行，以后要注释掉
        //tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //form login 提取出来
        applyPasswordAuthenticationConfig(http);
        //加上验证码拦截器在username password filter 前面
        http.apply(validateCodeSecurityConfig)
                .and()
                .rememberMe()
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(securityProperties.getBrowser().getRememberMe())
                .userDetailsService(userDetailsService)
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
