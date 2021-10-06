package com.imooc.core.support;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : Knight
 * @date : 2021/8/31 11:03 上午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleResponse {
    private Object content;
}
