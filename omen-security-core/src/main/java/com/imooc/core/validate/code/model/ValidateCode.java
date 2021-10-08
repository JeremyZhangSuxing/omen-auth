package com.imooc.core.validate.code.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author : Knight
 * @date : 2021/9/3 11:17 上午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidateCode implements Serializable {

    protected String code;
    protected LocalDateTime expireTime;

    public ValidateCode(String code, int expire) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expire);
    }


    public boolean isExpired() {
        return this.expireTime.isBefore(LocalDateTime.now());
    }

}
