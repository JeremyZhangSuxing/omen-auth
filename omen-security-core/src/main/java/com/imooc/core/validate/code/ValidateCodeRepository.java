package com.imooc.core.validate.code;

import com.imooc.core.validate.code.model.ValidateCode;
import com.imooc.core.validate.code.support.ValidateCodeType;
import org.springframework.web.context.request.ServletWebRequest;

public interface ValidateCodeRepository{

	/**
	 * 保存验证码
	 */
	void save(ServletWebRequest request, ValidateCode code, ValidateCodeType validateCodeType);
	/**
	 * 获取验证码
	 */
	ValidateCode get(ServletWebRequest request, ValidateCodeType validateCodeType);
	/**
	 * 移除验证码
	 */
	void remove(ServletWebRequest request, ValidateCodeType codeType);

}