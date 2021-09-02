package com.imooc.browser;


import com.imooc.core.auth.ImoocAuthenticationFailureHandler;
import com.imooc.core.auth.ImoocAuthenticationSuccessHandler;
import com.imooc.core.auth.ImoocAuthenticationSuccessProHandler;
import com.imooc.core.properties.SecurityProperties;
import com.imooc.core.validate.code.ImageCodeGenerator;
import com.imooc.core.validate.code.ValidateFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
    private final ImoocAuthenticationSuccessProHandler imoocAuthenticationSuccessProHandler;
    private final ImoocAuthenticationFailureHandler imoocAuthenticationFailureHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @ConditionalOnMissingBean(value = ImageCodeGenerator.class)
    public ImageCodeGenerator imageCodeGenerator() {
        return new ImageCodeGenerator(securityProperties);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ValidateFilter validateFilter = new ValidateFilter();
        validateFilter.setImoocAuthenticationFailureHandler(imoocAuthenticationFailureHandler);
        validateFilter.setSecurityProperties(securityProperties);
        //设置认证方式
        http.addFilterBefore(validateFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                .loginPage("/authentication/require")
                .loginProcessingUrl("/authentication/form")
                .successHandler(imoocAuthenticationSuccessProHandler)
                .failureHandler(imoocAuthenticationFailureHandler)

                .and()
                //对请求进行授权
                .authorizeRequests()
                .antMatchers(securityProperties.getBrowser().getLoginPage(),
                        "/authentication/require",
                        "/code/image").permitAll()
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
