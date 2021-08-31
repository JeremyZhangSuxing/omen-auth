package com.imooc.browser.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : Knight
 * @date : 2021/8/31 2:35 下午
 */
@Slf4j
@RequiredArgsConstructor
@Component("imoocAuthenticationSuccessHandler")
public class ImoocAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final ObjectMapper objectMapper;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        log.info(">>>>>>>>>>>>>>登录成功<<<<<<<<<<<<<");
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        //向接口响应中写入认证信息
        response.getWriter().write(objectMapper.writeValueAsString(authentication));
    }
}
