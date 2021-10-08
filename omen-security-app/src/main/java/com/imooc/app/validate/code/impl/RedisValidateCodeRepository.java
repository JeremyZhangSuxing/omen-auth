package com.imooc.app.validate.code.impl;

import com.imooc.core.validate.code.ValidateCodeException;
import com.imooc.core.validate.code.ValidateCodeRepository;
import com.imooc.core.validate.code.model.ValidateCode;
import com.imooc.core.validate.code.support.ValidateCodeType;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.concurrent.TimeUnit;

/**
 * @author : Knight
 * @date : 2021/10/7 10:29 上午
 */
@Component
@RequiredArgsConstructor
public class RedisValidateCodeRepository implements ValidateCodeRepository {
    private static final String SESSION_KEY_PREFIX = "REDIS_SESSION_KEY_PREFIX_";
    private static final String TAG = "_";
    private final RedisTemplate<String, Object> customerRedisTemplate;


    @Override
    public void save(ServletWebRequest request, ValidateCode code, ValidateCodeType validateCodeType) {
        customerRedisTemplate.opsForValue().set(buildKey(request, validateCodeType), code, 1 , TimeUnit.MINUTES);
    }

    @Override
    public ValidateCode get(ServletWebRequest request, ValidateCodeType validateCodeType) {
        return (ValidateCode) customerRedisTemplate.opsForValue().get(buildKey(request, validateCodeType));
    }

    @Override
    public void remove(ServletWebRequest request, ValidateCodeType codeType) {
        customerRedisTemplate.delete(buildKey(request, codeType));
    }

    /**
     * 构建app 手机号码session key 每个号码的key 是唯一的
     */
    private String buildKey(ServletWebRequest request, ValidateCodeType type) {
        String deviceId = request.getHeader("deviceId");
        if (StringUtils.isBlank(deviceId)) {
            throw new ValidateCodeException("请在请求头中携带deviceId参数");
        }
        String mobile = request.getParameter("mobile");
        return SESSION_KEY_PREFIX + mobile + TAG + type.toString().toLowerCase() + TAG + deviceId;
    }

}
