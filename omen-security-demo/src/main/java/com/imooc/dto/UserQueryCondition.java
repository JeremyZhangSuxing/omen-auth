/**
 * 
 */
package com.imooc.dto;

import lombok.Data;

/**
 * @author zhangyang
 *
 */
@Data
public class UserQueryCondition {
	
	private String username;
	
//	@ApiModelProperty(value = "用户年龄起始值")
	private int age;
//	@ApiModelProperty(value = "用户年龄终止值")
	private int ageTo;
}
