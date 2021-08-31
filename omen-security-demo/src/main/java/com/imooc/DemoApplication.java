package com.imooc;

import com.imooc.browser.SecurityConfig;
import com.imooc.browser.web.controller.BrowserController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : Knight
 * @date : 2021/8/29 10:37 上午
 */
@RestController
@SpringBootApplication(scanBasePackageClasses = {SecurityConfig.class, DemoApplication.class})
public class DemoApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(DemoApplication.class, args);
        System.out.print(run.getBean(BrowserController.class).toString());
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello spring security!!!";
    }
}
