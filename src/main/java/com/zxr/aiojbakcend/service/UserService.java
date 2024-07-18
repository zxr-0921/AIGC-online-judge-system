package com.zxr.aiojbakcend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxr.aiojbakcend.common.BaseResponse;
import com.zxr.aiojbakcend.model.domain.User;
import com.zxr.aiojbakcend.model.dto.user.request.UserLoginRequest;

/**
* @author zxr0921
* @description 针对表【user】的数据库操作Service
* @createDate 2024-07-17 11:38:38
*/
public interface UserService extends IService<User>{

    BaseResponse login(UserLoginRequest userLoginRequest);
}
