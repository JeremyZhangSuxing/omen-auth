package com.imooc.core.validate.code.filter;

import com.imooc.core.properties.SecurityProperties;
import com.imooc.core.validate.code.SecurityConstants;
import com.imooc.core.validate.code.ValidateCodeException;
import com.imooc.core.validate.code.support.ValidateProcessorHolder;
import com.imooc.core.validate.code.support.ValidateCodeType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
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
    // 为何替换成默认的处理器？？？
    private final AuthenticationFailureHandler authenticationFailureHandler;
    private final SecurityProperties securityProperties;
    private final Map<String, ValidateCodeType> urlMap = new HashMap<>(16);
    /**
     * 验证请求url与配置的url是否匹配的工具类
     */
    private AntPathMatcher pathMatcher = new AntPathMatcher();

    /**
     * 图像验证码代码优化
     * 验证码的基本参数可配：图片大小、验证码长度、验证码有效时间
     * 验证码拦截的接口可配置：即哪些接口需要执行验证码的拦截逻辑
     * 验证码的生成逻辑可配
     */
    @Override
    public void afterPropertiesSet() {
        urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM, ValidateCodeType.IMAGE);
        addUrl(securityProperties.getImage().getUrls(), ValidateCodeType.IMAGE);

        urlMap.put(SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE, ValidateCodeType.SMS);
        addUrl(securityProperties.getSms().getUrl(), ValidateCodeType.SMS);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ValidateCodeType type = getValidateCodeType(request);
        if (type != null) {
            logger.info("校验请求(" + request.getRequestURI() + ")中的验证码,验证码类型" + type);
            try {
                validateProcessorHolder.findValidateProcessor(type)
                        .validate(new ServletWebRequest(request, response));
                logger.info("验证码校验通过");
            } catch (ValidateCodeException exception) {
                authenticationFailureHandler.onAuthenticationFailure(request, response, exception);
                return;
            }
        }
        filterChain.doFilter(request, response);

    }

    private synchronized void addUrl(Set<String> urls, ValidateCodeType validateType) {
        if (CollectionUtils.isNotEmpty(urls)) {
            for (String url : urls) {
                urlMap.put(url, validateType);
            }
        }
    }


    /**
     * 获取校验码的类型，如果当前请求不需要校验，则返回null
     *
     * @param request
     * @return
     */
    private ValidateCodeType getValidateCodeType(HttpServletRequest request) {
        ValidateCodeType result = null;
        if (!StringUtils.equalsIgnoreCase(request.getMethod(), HttpMethod.GET.name())) {
            Set<String> urls = urlMap.keySet();
            for (String url : urls) {
                if (pathMatcher.match(url, request.getRequestURI())) {
                    result = urlMap.get(url);
                }
            }
        }
        return result;
    }
}
