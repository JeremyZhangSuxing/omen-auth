package com.imooc;

import com.imooc.browser.BrowserSecurityConfig;
import com.imooc.browser.web.controller.BrowserController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : Knight
 * @date : 2021/8/29 10:37 上午
 */
@Slf4j
@RestController
@SpringBootApplication(scanBasePackageClasses = {BrowserSecurityConfig.class, DemoApplication.class})
public class DemoApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(DemoApplication.class, args);
        log.info("DemoApplication  < ------ >  {}", run.getBean(BrowserController.class));
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello spring security!!!";
    }
}
