package com.zxr.aiojbakcend.service.impl;

import cn.hutool.jwt.JWT;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.zxr.aiojbakcend.common.BaseResponse;
import com.zxr.aiojbakcend.common.ErrorCode;
import com.zxr.aiojbakcend.common.ResultUtils;
import com.zxr.aiojbakcend.constant.JWTConstant;
import com.zxr.aiojbakcend.constant.UserConstant;
import com.zxr.aiojbakcend.exception.BusinessException;
import com.zxr.aiojbakcend.mapper.UserMapper;
import com.zxr.aiojbakcend.model.domain.User;
import com.zxr.aiojbakcend.model.dto.user.request.UserLoginRequest;
import com.zxr.aiojbakcend.security.entity.LoginUser;
import com.zxr.aiojbakcend.service.UserService;
import com.zxr.aiojbakcend.utils.VerificationToolUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;

/**
 * @author zxr0921
 * @description 针对表【user】的数据库操作Service实现
 * @createDate 2024-07-17 11:38:38
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public BaseResponse login(UserLoginRequest userLoginRequest) {
        // 校验参数
        String phone = userLoginRequest.getPhone();
        String password = userLoginRequest.getPassword();
        // 校验手机号
        if (!VerificationToolUtils.checkMobilePhone(phone)) {
            throw new BusinessException(ErrorCode.PHONE_ERROR);
        }
        // 封装认证信息
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(phone, password);

        // 调用认证管理器进行认证
        Authentication authenticated = authenticationManager.authenticate(authenticationToken);

        // 在Authentication中获取用户信息
        LoginUser loginUser = (LoginUser) authenticated.getPrincipal();
        String userId = loginUser.getUser().getId().toString();

        // 序列化loginUser
        String loginUserJson = new Gson().toJson(loginUser);

        System.out.println("序列化后的对象"+loginUserJson);

        // 用户信息存到redis
        stringRedisTemplate.opsForValue().set(UserConstant.USER_LOGIN_STATE + userId, loginUserJson);
        // 设置过期时间
        stringRedisTemplate.expire(UserConstant.USER_LOGIN_STATE + userId, Duration.ofMinutes(30));

        // 生成JWT
        // todo:签名还需修改
        String jwtToken = JWT.create()
                .setPayload("userId", userId)
                .setPayload("phone", phone)
                .setKey(JWTConstant.SALT)
                .sign();
        // 返回token
        HashMap<String, String> map = new HashMap<>();
        map.put("token", jwtToken);

        return ResultUtils.success(map);
    }
}




