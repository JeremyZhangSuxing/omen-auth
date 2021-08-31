package com.imooc.core.validate.code;

import com.imooc.core.auth.ImoocAuthenticationFailureHandler;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author suxing.zhang
 * @date 2021/8/31 22:24
 **/
@RequiredArgsConstructor
public class ValidateFilter extends OncePerRequestFilter {
    private final ImoocAuthenticationFailureHandler imoocAuthenticationFailureHandler;
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (StringUtils.equals("/authentication/form", request.getRequestURI()) && StringUtils.equalsIgnoreCase(HttpMethod.POST.name(), request.getMethod())) {

        }
    }

    private void validateCode(ServletWebRequest request) throws ServletRequestBindingException {
        ImageCode codeInSession = (ImageCode) sessionStrategy.getAttribute(request, ValidateCodeController.SESSION_KEY);
        String codeRequest = ServletRequestUtils.getStringParameter(request.getRequest(), "imageCode");

        if (StringUtils.isBlank(codeRequest)) {
            throw new ValidateCodeException("验证码不能为空");
        }

        if (null==codeInSession) {
            throw new ValidateCodeException("验证码信息不存在");
        }
        if(codeInSession.isExpired()) {
            throw new ValidateCodeException("验证码已经过期");

        }
    }
}
