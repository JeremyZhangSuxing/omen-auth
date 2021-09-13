package com.imooc.core.validate.code.config;

import com.imooc.core.validate.code.filter.ValidateCodeFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * @author suxing.zhang
 * @date 2021/9/12 13:38
 **/
@Component
@RequiredArgsConstructor
public class ValidateCodeSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final ValidateCodeFilter validateCodeFilter;

    @Override
    public void configure(HttpSecurity httpBuilder) throws Exception {
        httpBuilder.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class);

    }

}
