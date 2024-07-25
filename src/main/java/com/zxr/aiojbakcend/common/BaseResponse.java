package com.zxr.aiojbakcend.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 通用返回类
 *
 * @param <T>
 */
@Data
@AllArgsConstructor
public class BaseResponse<T> implements Serializable {
    private int code;
    private T data;
    private String message;

    public BaseResponse(ResultStatus resultStatus, T data) {
        this.code = resultStatus.getStatus();
        this.message = resultStatus.getDescription();
        this.data = data;
    }

    public BaseResponse(ResultStatus resultStatus, T data, String mes) {
        this.code = resultStatus.getStatus();
        this.message = mes;
        this.data = data;
    }
}
