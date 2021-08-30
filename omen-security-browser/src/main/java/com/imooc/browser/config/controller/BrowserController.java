package com.imooc.browser.config.controller;

import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : Knight
 * @date : 2021/8/30 6:21 下午
 */
@Controller
public class BrowserController {
    private RequestCache requestCache = new HttpSessionRequestCache();


    public String requireAuthentication(HttpServletRequest request, HttpServletResponse response) {
        return "";
    }
}
