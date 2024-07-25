package com.zxr.aiojbakcend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxr.aiojbakcend.common.BaseResponse;
import com.zxr.aiojbakcend.model.domain.User;
import com.zxr.aiojbakcend.model.dto.user.request.UserLoginRequest;
import com.zxr.aiojbakcend.model.dto.user.request.UserRegisterRequest;
import com.zxr.aiojbakcend.model.vo.LoginUserVO;
import jakarta.servlet.http.HttpServletRequest;

/**
* @author zxr0921
* @description 针对表【user】的数据库操作Service
* @createDate 2024-07-17 11:38:38
*/
public interface UserService extends IService<User>{

    /**
     * 用户登录
     * @param userLoginRequest
     * @return
     */
    BaseResponse login(UserLoginRequest userLoginRequest);

    /**
     * 用户登出
     * @return
     */
    boolean logout();

    /**
     * 用户注册
     * @param userRegisterRequest
     * @return
     */
    boolean register(UserRegisterRequest userRegisterRequest);

    /**
     * 获取当前登录用户信息
     * @param request
     * @return
     */
    LoginUserVO getLoginUser(HttpServletRequest request);
}
