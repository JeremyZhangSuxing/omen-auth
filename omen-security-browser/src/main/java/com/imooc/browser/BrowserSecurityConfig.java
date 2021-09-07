package com.imooc.browser;


import com.imooc.core.auth.ImoocAuthenticationFailureHandler;
import com.imooc.core.auth.ImoocAuthenticationSuccessProHandler;
import com.imooc.core.properties.SecurityProperties;
import com.imooc.core.validate.code.ImageCodeGenerator;
import com.imooc.core.validate.code.SmsCodeAuthenticationSecurityConfig;
import com.imooc.core.validate.code.SmsValidateFilter;
import com.imooc.core.validate.code.ValidateCodeFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * @author : Knight
 * @date : 2021/8/30 9:55 上午
 */
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(SecurityProperties.class)
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    private final SecurityProperties securityProperties;
    //    private final ImoocAuthenticationSuccessHandler imoocAuthenticationSuccessHandler;
    private final ImoocAuthenticationSuccessProHandler imoocAuthenticationSuccessProHandler;
    private final ImoocAuthenticationFailureHandler imoocAuthenticationFailureHandler;
    private final DataSource dataSource;
    private final UserDetailsService userDetailsService;
    private final SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

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
        ValidateCodeFilter validateFilter = new ValidateCodeFilter();
        validateFilter.setImoocAuthenticationFailureHandler(imoocAuthenticationFailureHandler);
        validateFilter.setSecurityProperties(securityProperties);
        //smsValidate filter config
        SmsValidateFilter smsValidateFilter = new SmsValidateFilter();
        smsValidateFilter.setImoocAuthenticationFailureHandler(imoocAuthenticationFailureHandler);
        smsValidateFilter.setSecurityProperties(securityProperties);
        smsValidateFilter.afterPropertiesSet();

        //设置认证方式
        http
                .addFilterBefore(smsValidateFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(validateFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                .loginPage("/authentication/require")
                .loginProcessingUrl("/authentication/form")
                .successHandler(imoocAuthenticationSuccessProHandler)
                .failureHandler(imoocAuthenticationFailureHandler)
                .and()
                .rememberMe()
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(securityProperties.getBrowser().getRememberMe())
                .userDetailsService(userDetailsService)
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
                .csrf().disable()
                .apply(smsCodeAuthenticationSecurityConfig);
    }
}
