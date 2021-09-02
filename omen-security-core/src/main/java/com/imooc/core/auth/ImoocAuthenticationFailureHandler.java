package com.imooc.core.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.core.properties.BrowserProperties;
import com.imooc.core.properties.SecurityProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author suxing.zhang
 * @date 2021/8/31 21:30
 **/
@Slf4j
@RequiredArgsConstructor
@Component("imoocAuthenticationFailureHandler")
public class ImoocAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private final ObjectMapper objectMapper;
    private final SecurityProperties securityProperties;

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        log.info(">>>>>>>>>>>>>>登录失败<<<<<<<<<<<<<");
        if (BrowserProperties.LoginType.JSON.equals(securityProperties.getBrowser().getLoginType())) {
            log.info(">>>>>>>>>>>>>>登录失败<<<<<<<<<<<<<");
            httpServletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            httpServletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            httpServletResponse.getWriter().write(objectMapper.writeValueAsString(e));
        } else {
            super.onAuthenticationFailure(httpServletRequest, httpServletResponse, e);
        }
    }
}
