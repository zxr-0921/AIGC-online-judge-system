package com.zxr.aiojbakcend.model.dto.user.request;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录请求
 */
@Schema(name = "UserLoginRequest", description = "用户登录",required = true)
@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    @Schema(name = "phone", description = "手机号",required = true)
    private String phone;

    @Schema(name = "password", description = "密码", required = true)
    private String password;
}
