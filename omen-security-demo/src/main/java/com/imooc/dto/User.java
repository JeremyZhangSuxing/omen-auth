/**
 *
 */
package com.imooc.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author zhailiang
 *
 */
@Data
public class User {
    public interface UserSimpleView {
    }

    public interface UserDetailView extends UserSimpleView {
    }

    private String id;

    //	@MyConstraint(message = "这是一个测试")
//	@ApiModelProperty(value = "用户名")
    private String username;

    //	@NotBlank(message = "密码不能为空")
    private String password;

    //	@Past(message = "生日必须是过去的时间")
    private Date birthday;


}
