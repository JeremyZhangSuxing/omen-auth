package com.imooc.core.properties;

import lombok.Data;

/**
 * @author : Knight
 * @date : 2021/9/3 1:18 下午
 */
@Data
public class SmsCodeProperties {
    private int length = 4;
    private int expire = 60;
}
