package com.imooc.core.social;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * @author suxing.zhang
 * @date 2021/9/25 11:14
 **/
@Configuration
@EnableSocial
public class SocialConfiguration extends SocialConfigurerAdapter {

    @Qualifier("dataSource")
    @Autowired(required = false)
    private DataSource dataSource;

    /**
     * 演示使用明文
     */
    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        JdbcUsersConnectionRepository jdbcUsersConnectionRepository = new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
        jdbcUsersConnectionRepository.setTablePrefix("ww_");
        return jdbcUsersConnectionRepository;
    }

    /**
     * SpringSocialConfigurer 导入SocialAuthenticationFilter 会对特定的请求进行社交认证登录的拦截
     * /auth/qq  /auth 默认拦截请求  /providerId
     */
    @Bean
    public SpringSocialConfigurer imoocSocialSecurityConfig(){
        return new SpringSocialConfigurer();
    }
}
