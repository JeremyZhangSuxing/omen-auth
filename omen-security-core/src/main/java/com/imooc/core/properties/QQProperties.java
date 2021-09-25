package com.imooc.core.properties;

import lombok.Data;
import org.springframework.boot.autoconfigure.social.SocialProperties;

/**
 * @author suxing.zhang
 * @date 2021/9/25 11:30
 **/
@Data
public class QQProperties extends SocialProperties {
    private String providerId;
}
