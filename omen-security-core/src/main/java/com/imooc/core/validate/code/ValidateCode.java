package com.imooc.core.validate.code;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author : Knight
 * @date : 2021/9/3 11:17 上午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidateCode {
    protected String code;
    protected LocalDateTime expireTime;

    public ValidateCode(String code, int expire) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expire);
    }
}
