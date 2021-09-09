package com.imooc.core.validate.code.filter;

import com.imooc.core.auth.ImoocAuthenticationFailureHandler;
import com.imooc.core.auth.ImoocAuthenticationSuccessProHandler;
import com.imooc.core.properties.SecurityProperties;
import com.imooc.core.validate.code.SecurityConstants;
import com.imooc.core.validate.code.support.ValidateType;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author suxing.zhang
 * @date 2021/9/8 21:59
 **/
@Component("validateCodeFilter")
@RequiredArgsConstructor
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {


    private final ImoocAuthenticationSuccessProHandler imoocAuthenticationSuccessProHandler;
    private final ImoocAuthenticationFailureHandler imoocAuthenticationFailureHandler;
    private final SecurityProperties securityProperties;

    private Map<String, ValidateType> urlMap = new HashMap<>(16);

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public void afterPropertiesSet() {
        urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM, ValidateType.IMAGE);
        addUrl(securityProperties.getImage().getUrls(), ValidateType.IMAGE);

        urlMap.put(SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE, ValidateType.SMS);
        addUrl(securityProperties.getSms().getUrl(), ValidateType.SMS);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

    }

    private synchronized void addUrl(Set<String> urls, ValidateType validateType) {
        if (CollectionUtils.isNotEmpty(urls)) {
            for (String url : urls) {
                urlMap.put(url, validateType);
            }
        }
    }

}
