package com.imooc.core.properties;

import lombok.Data;

/**
 * @author suxing.zhang
 * @date 2021/9/25 11:32
 **/
@Data
public class CommonSocialProperties {
    private QQProperties qq = new QQProperties();
    private String processUrl = "/auth";

}
