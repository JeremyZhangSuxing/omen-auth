package com.imooc.core.validate.code;

import com.imooc.core.auth.ImoocAuthenticationFailureHandler;
import com.imooc.core.properties.SecurityProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.AntPathMatcher;
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
@Data
@Slf4j
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ValidateCodeFilter extends OncePerRequestFilter {
    private ImoocAuthenticationFailureHandler imoocAuthenticationFailureHandler;
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
    private AntPathMatcher antPathMatcher = new AntPathMatcher();
    private SecurityProperties securityProperties;

    /**
     * 图像验证码代码优化
     * 验证码的基本参数可配：图片大小、验证码长度、验证码有效时间
     * 验证码拦截的接口可配置：即哪些接口需要执行验证码的拦截逻辑
     * 验证码的生成逻辑可配
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (shouldBeValidate(request.getRequestURI())) {
            log.info(">>>>>>>>>>>> 验证码校验开始 <<<<<<<<<<<<<<<<<, 配置的url----->>>>" + request.getRequestURI());
            try {
                validateCode(new ServletWebRequest(request, response));
            } catch (ValidateCodeException e) {
                log.error(">>>>>>>>>>>> 验证码校验失败 <<<<<<<<<<<<<<<<<", e);
                imoocAuthenticationFailureHandler.onAuthenticationFailure(request, response, e);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private void validateCode(ServletWebRequest request) throws ServletRequestBindingException {
        //从当前的请求中获取到验证码信息
        ImageCode codeInSession = (ImageCode) sessionStrategy.getAttribute(request, ValidateCodeController.SESSION_KEY);
        String codeRequest = ServletRequestUtils.getStringParameter(request.getRequest(), "imageCode");

        if (StringUtils.isBlank(codeRequest)) {
            throw new ValidateCodeException("验证码不能为空");
        }
        if (null == codeInSession) {
            throw new ValidateCodeException("验证码信息不存在");
        }
        if (codeInSession.isExpired()) {
            throw new ValidateCodeException("验证码已经过期");
        }
        if (!StringUtils.equals(codeRequest, codeInSession.getCode())) {
            throw new ValidateCodeException("输入的验证码不正确");
        }
    }

    private boolean shouldBeValidate(String requestUrl) {
        return securityProperties.getImage()
                .getUrls()
                .stream()
                .anyMatch(configUrl -> antPathMatcher.match(configUrl, requestUrl));
    }
}
