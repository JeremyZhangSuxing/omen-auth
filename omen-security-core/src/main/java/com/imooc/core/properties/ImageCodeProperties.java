package com.imooc.core.properties;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author : Knight
 * @date : 2021/9/2 9:47 上午
 */
@Data
public class ImageCodeProperties {
    private int width = 30;
    private int height = 30;
    private int expire = 60;
    private Set<String> urls = new HashSet<>(Stream.of("/authentication/form").collect(Collectors.toList()));
}
