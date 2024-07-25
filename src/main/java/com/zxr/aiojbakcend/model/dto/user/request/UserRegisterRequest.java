package com.zxr.aiojbakcend.model.dto.user.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求
 */
@Schema(name = "UserRegisterRequest", description = "用户注册",required = true)
@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    @Schema(name = "phone", description = "手机号",required = true)
    private String phone;

    @Schema(name = "password", description = "密码", required = true)
    private String password;

    @Schema(name = "checkPassword", description = "确认密码", required = true)
    private String checkPassword;
}
