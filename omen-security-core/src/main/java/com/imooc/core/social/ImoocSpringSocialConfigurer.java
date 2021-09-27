package com.imooc.core.social;

import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * @author suxing.zhang
 * @date 2021/9/27 7:19
 **/
public class ImoocSpringSocialConfigurer extends SpringSocialConfigurer {
    private final String filterProcessUrl;

    public ImoocSpringSocialConfigurer(String filterProcessUrl) {
        this.filterProcessUrl = filterProcessUrl;
    }

    @Override
    protected <T> T postProcess(T object) {
        SocialAuthenticationFilter socialAuthenticationFilter = (SocialAuthenticationFilter) super.postProcess(object);
        socialAuthenticationFilter.setFilterProcessesUrl(filterProcessUrl);
        return (T) socialAuthenticationFilter;
    }
}
