package com.zxr.aiojbakcend.controller;

import com.zxr.aiojbakcend.common.BaseResponse;
import com.zxr.aiojbakcend.common.ErrorCode;
import com.zxr.aiojbakcend.common.ResultUtils;
import com.zxr.aiojbakcend.exception.BusinessException;
import com.zxr.aiojbakcend.model.dto.user.request.UserLoginRequest;
import com.zxr.aiojbakcend.model.vo.LoginUserVo;
import com.zxr.aiojbakcend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    //todo:测试
    @GetMapping("/hello")
    @PreAuthorize("hasAnyAuthority('user_admin')")
    public String hello() {
        return "Hello, World!";
    }

    @PostMapping("/login")
    public BaseResponse<Long> login(@RequestBody UserLoginRequest userLoginRequest) {
        if (userLoginRequest == null) {
           throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 调用service层
        // 返回
        return userService.login(userLoginRequest);
    }


}
