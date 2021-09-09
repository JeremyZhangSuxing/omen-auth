package com.imooc.core.validate.code.filter;

import com.imooc.core.auth.ImoocAuthenticationFailureHandler;
import com.imooc.core.properties.SecurityProperties;
import com.imooc.core.validate.code.SecurityConstants;
import com.imooc.core.validate.code.ValidateCodeException;
import com.imooc.core.validate.code.support.ValidateProcessorHolder;
import com.imooc.core.validate.code.support.ValidateType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
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
@Slf4j
@Component("validateCodeFilter")
@RequiredArgsConstructor
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {

    private final ValidateProcessorHolder validateProcessorHolder;
    private final ImoocAuthenticationFailureHandler imoocAuthenticationFailureHandler;
    private final SecurityProperties securityProperties;
    private final Map<String, ValidateType> urlMap = new HashMap<>(16);

    @Override
    public void afterPropertiesSet() {
        urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM, ValidateType.IMAGE);
        addUrl(securityProperties.getImage().getUrls(), ValidateType.IMAGE);

        urlMap.put(SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE, ValidateType.SMS);
        addUrl(securityProperties.getSms().getUrl(), ValidateType.SMS);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (shouldBeValidate(request.getRequestURI())) {
            ValidateType validateType = urlMap.get(request.getRequestURI());
            try {
                validateProcessorHolder.findValidateProcessor(validateType)
                        .validate(new ServletWebRequest(request, response));
            } catch (ValidateCodeException e) {
                log.error(" {}  校验类型 >>> 验证码校验失败 {} ", validateType, e);
                imoocAuthenticationFailureHandler.onAuthenticationFailure(request, response, e);
            }
            filterChain.doFilter(request, response);
        }
    }

    private synchronized void addUrl(Set<String> urls, ValidateType validateType) {
        if (CollectionUtils.isNotEmpty(urls)) {
            for (String url : urls) {
                urlMap.put(url, validateType);
            }
        }
    }

    private boolean shouldBeValidate(String requestUrl) {
        return urlMap.containsKey(requestUrl);
    }
}
