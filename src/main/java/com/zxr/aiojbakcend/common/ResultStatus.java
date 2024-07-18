package com.zxr.aiojbakcend.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 返回结果状态
 */
@Getter
@AllArgsConstructor
public enum ResultStatus {
    /**
     * 成功
     */
    SUCCESS(0, "成功"),
    /**
     * 失败
     */
    FAIL(1, "失败");
    private final int status;
    private final String description;
}
