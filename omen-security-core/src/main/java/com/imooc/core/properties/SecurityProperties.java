package com.imooc.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author : Knight
 * @date : 2021/8/31 1:53 下午
 */
@Data
@ConfigurationProperties(prefix = "imooc-security")
public class SecurityProperties {
    private BrowserProperties browser = new BrowserProperties();
}