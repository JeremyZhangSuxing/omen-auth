package com.imooc.browser.auth;

import com.imooc.core.properties.BrowserProperties;
import com.imooc.core.properties.SecurityProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : Knight
 * @date : 2021/8/31 4:26 下午
 */
@Slf4j
@RequiredArgsConstructor
@Component("imoocAuthenticationSuccessProHandler")
public class ImoocAuthenticationSuccessProHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private final SecurityProperties securityProperties;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws ServletException, IOException {
        log.info(">>>>>>>登陆成功<<<<<<<：{}", securityProperties.getBrowser().getLoginType());

        if (BrowserProperties.LoginType.JSON == securityProperties.getBrowser().getLoginType()) {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            log.info(ReflectionToStringBuilder.toString(authentication, ToStringStyle.MULTI_LINE_STYLE));
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
