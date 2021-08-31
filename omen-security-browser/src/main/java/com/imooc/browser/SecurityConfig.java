package com.imooc.browser;

import com.imooc.browser.auth.ImoocAuthenticationSuccessHandler;
import com.imooc.core.properties.SecurityProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author : Knight
 * @date : 2021/8/30 9:55 上午
 */
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final SecurityProperties securityProperties;
    private final ImoocAuthenticationSuccessHandler imoocAuthenticationSuccessHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //设置认证方式
        http.formLogin()
                .loginPage("/authentication/require")
                .loginProcessingUrl("/authentication/form")
                .successHandler(imoocAuthenticationSuccessHandler)
                .and()
                //对请求进行授权
                .authorizeRequests()
                .antMatchers(securityProperties.getBrowser().getLoginPage(), "/authentication/require").permitAll()
                //认证请求
                .anyRequest()
                //都需要身份认证
                .authenticated()
                .and()
                //跨域配置
                .csrf().disable()
        ;
    }
}
