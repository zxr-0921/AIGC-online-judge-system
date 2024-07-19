package com.zxr.aiojbakcend.security.filter;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.google.gson.Gson;
import com.zxr.aiojbakcend.common.ErrorCode;
import com.zxr.aiojbakcend.constant.JWTConstant;
import com.zxr.aiojbakcend.constant.UserConstant;
import com.zxr.aiojbakcend.exception.BusinessException;
import com.zxr.aiojbakcend.security.entity.LoginUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 1.获取请求头中的token
        String token = request.getHeader("token");
        // 判断token是否为空
        if (StringUtils.isBlank(token)) {
            // 如果token为空，放行
            filterChain.doFilter(request, response);
            return;
        }

        // 2.解析token获取用户id
        JWT jwt = JWTUtil.parseToken(token);
        String userId = jwt.getPayload("userId").toString();
        // 从redis中获取token
        String loginUserJson = stringRedisTemplate.opsForValue().get(UserConstant.USER_LOGIN_STATE + userId);

        // 判断redis中的loginUser是否为空
        if (StringUtils.isBlank(loginUserJson)) {
            // 如果为空，说明token已经失效
            throw new BusinessException(ErrorCode.TOKEN_EXPIRED);
        }

        // 反序列化
        LoginUser loginUser = new Gson().fromJson(loginUserJson, LoginUser.class);

        // 封装认证信息
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser,
                        null, loginUser.getAuthorities());

        // 将用户信息放入Security全局上下文
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        // 放行
        filterChain.doFilter(request, response);
    }
}
