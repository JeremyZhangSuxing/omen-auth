package com.imooc.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

/**
 * @author : Knight
 * @date : 2021/8/30 5:20 下午
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class LocalUserDetailService implements UserDetailsService, SocialUserDetailsService {
    private final PasswordEncoder passwordEncoder;
    private static final String PASSWORD = "123456";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //真实的业务系统中可以去数据库获取用户信息 如密码和权限
        log.info("username  -----> {}", username);
        return new User(username, passwordEncoder.encode(PASSWORD),
                //权限
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }

    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        log.info("username  -----> {}", userId);
        return new SocialUser(userId, passwordEncoder.encode(PASSWORD),
                //权限
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }

}
