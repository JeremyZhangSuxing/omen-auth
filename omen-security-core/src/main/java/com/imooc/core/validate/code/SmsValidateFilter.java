//package com.imooc.core.validate.code;
//
//import com.imooc.browser.authentication.ImoocAuthenticationFailureHandler;
//import com.imooc.core.properties.SecurityProperties;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.social.connect.web.HttpSessionSessionStrategy;
//import org.springframework.social.connect.web.SessionStrategy;
//import org.springframework.util.AntPathMatcher;
//import org.springframework.web.bind.ServletRequestBindingException;
//import org.springframework.web.context.request.ServletWebRequest;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.HashSet;
//import java.util.Set;
//
///**
// * @author suxing.zhang
// * @date 2021/9/4 9:53
// **/
//@Data
//@Slf4j
//@NoArgsConstructor
//@AllArgsConstructor
//public class SmsValidateFilter extends OncePerRequestFilter implements InitializingBean {
//
//
//    private ImoocAuthenticationFailureHandler imoocAuthenticationFailureHandler;
//    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
//    private AntPathMatcher antPathMatcher = new AntPathMatcher();
//    private SecurityProperties securityProperties;
//    private Set<String> urls = new HashSet<>();
//
//    @Override
//    public void afterPropertiesSet() {
//        urls.addAll(securityProperties.getSms().getUrl());
//        urls.add("/authentication/mobile");
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        if (shouldBeValidate(request.getRequestURI())) {
//            log.info(">>>>>>>>>>>> 短信验证码校验开始 <<<<<<<<<<<<<<<<<, 请求的url----->>>>" + request.getRequestURI());
//            try {
//                validateCode(new ServletWebRequest(request, response));
//            } catch (ValidateCodeException e) {
//                log.error(">>>>>>>>>>>> 短信验证码校验开始 <<<<<<<<<<<<<<<<<", e);
//                imoocAuthenticationFailureHandler.onAuthenticationFailure(request, response, e);
//                return;
//            }
//        }
//        filterChain.doFilter(request, response);
//    }
//
//    private void validateCode(ServletWebRequest request) throws ServletRequestBindingException {
//
//    }
//
//    private boolean shouldBeValidate(String requestUrl) {
//        return urls.stream()
//                .anyMatch(configUrl -> antPathMatcher.match(configUrl, requestUrl));
//    }
//}
