package com.zxr.aiojbakcend.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 错误码
 */
@Getter
@AllArgsConstructor
public enum ErrorCode {
    SYSTEM_ERROR(5001, "系统异常"),
    USER_NOT_FOUND(5002, "用户不存在"),
    PARAMS_ERROR(5003, "参数错误"),
    PHONE_ERROR(5004, "手机号格式错误"),
    PASSWORD_ERROR(5005, "密码错误"),
    TOKEN_EXPIRED(5006, "token过期"),
    CHECK_PASSWORD_ERROR(5007, "密码校验错误"),
    PASSWORD_LENGTH_ERROR(5008, "密码长度错误"),
    USER_EXIST(4001, "用户已存在"),
    NOT_LOGIN_ERROR(4002, "未登录");
    private final int code;
    private final String message;

}
