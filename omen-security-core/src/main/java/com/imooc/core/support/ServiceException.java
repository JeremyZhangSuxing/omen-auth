package com.imooc.core.support;

/**
 * @author : Knight
 * @date : 2021/10/7 4:27 下午
 */
public class ServiceException extends RuntimeException {
    public ServiceException(String msg) {
        super(msg);
    }
}
