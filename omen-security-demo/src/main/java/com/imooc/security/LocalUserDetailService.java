package com.imooc.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.AuthorityUtils;
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
        return buildUser(username);
    }

    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        log.info("username  -----> {}", userId);
        return buildUser(userId);
    }

    private SocialUserDetails buildUser(String userId) {
        // 根据用户名查找用户信息
        //根据查找到的用户信息判断用户是否被冻结
        String password = passwordEncoder.encode("123456");
        log.info("数据库密码是:" + password);

        return new SocialUser(userId, password,
                true, true, true, true,
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin,ROLE_USER"));
    }
}
