package com.zxr.aiojbakcend.controller;

import com.zxr.aiojbakcend.common.BaseResponse;
import com.zxr.aiojbakcend.common.ErrorCode;
import com.zxr.aiojbakcend.common.ResultUtils;
import com.zxr.aiojbakcend.exception.BusinessException;
import com.zxr.aiojbakcend.model.domain.User;
import com.zxr.aiojbakcend.model.dto.user.request.UserLoginRequest;
import com.zxr.aiojbakcend.model.dto.user.request.UserRegisterRequest;
import com.zxr.aiojbakcend.model.vo.LoginUserVO;
import com.zxr.aiojbakcend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "用户接口")
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    //todo:测试
    @GetMapping("/hello")
    @Operation(summary = "测试接口")
    @PreAuthorize("hasAnyAuthority('test')")
    public String hello() {
        return "Hello, World!";
    }

    /**\
     * 用户登录
     * @param userLoginRequest
     * @return
     */
    @PostMapping("/login")
    @Operation(summary = "用户登录")
    public BaseResponse<Long> login(@RequestBody UserLoginRequest userLoginRequest) {
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 调用service层
        // 返回
        return userService.login(userLoginRequest);
    }


    /**
     * 用户登出
     * @return
     */
    @PostMapping("/logout")
    @Operation(summary = "用户登出")
    public BaseResponse<String> logout() {
        // 调用service层
        boolean result = userService.logout();
        if (!result) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "登出失败");
        }
        // 返回
        return ResultUtils.success("登出成功");
    }

    /**
     * 用户注册
     * @param userRegisterRequest
     * @return
     */
    @PostMapping("/register")
    @Operation(summary = "用户注册")
    public BaseResponse<String> register(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 调用service层
        boolean register = userService.register(userRegisterRequest);
        if (!register) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "注册失败");
        }
        // 返回
        return ResultUtils.success("注册成功");
    }

    /**
     * 获取当前登录用户
     * @param request 请求
     * @return 当前登录用户信息
     */
    @Operation(summary = "获取当前登录用户")
    @GetMapping("/get/login")
    public BaseResponse<LoginUserVO> getLoginUser(HttpServletRequest request) {
        LoginUserVO loginUser = userService.getLoginUser(request);
        if(loginUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        return ResultUtils.success(loginUser);
    }

}
