package com.imooc.core.properties;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * @author : Knight
 * @date : 2021/9/3 1:18 下午
 */
@Data
public class SmsCodeProperties {
    private int length = 4;
    private int expire = 60;
    private Set<String> url = new HashSet<>();
}
