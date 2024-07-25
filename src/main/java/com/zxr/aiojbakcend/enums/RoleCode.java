package com.zxr.aiojbakcend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleCode {
    // 普通用户
    DEFAULT_USER(1002, "普通用户");

    // 角色编码
    private Integer roleCode;
    private String roleName;

}
