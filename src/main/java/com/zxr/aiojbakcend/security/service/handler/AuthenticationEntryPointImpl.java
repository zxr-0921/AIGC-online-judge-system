package com.zxr.aiojbakcend.security.service.handler;

import cn.hutool.http.HttpStatus;
import com.google.gson.Gson;
import com.zxr.aiojbakcend.common.BaseResponse;
import com.zxr.aiojbakcend.utils.WebUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 认证失败处理器
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        BaseResponse baseResponse = new BaseResponse(HttpStatus.HTTP_UNAUTHORIZED,
                null, "认证失败，请重新登录");
        WebUtils.renderString(response, new Gson().toJson(baseResponse));
    }
}
