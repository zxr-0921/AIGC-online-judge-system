package com.zxr.aiojbakcend.security.service.handler;

import cn.hutool.http.HttpStatus;
import com.google.gson.Gson;
import com.zxr.aiojbakcend.common.BaseResponse;
import com.zxr.aiojbakcend.utils.WebUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 权限失败处理器
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        BaseResponse baseResponse = new BaseResponse(HttpStatus.HTTP_FORBIDDEN,
                null, "权限不足");
        WebUtils.renderString(response, new Gson().toJson(baseResponse));
    }
}
