package com.zxr.aiojbakcend.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.jwt.JWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.zxr.aiojbakcend.common.BaseResponse;
import com.zxr.aiojbakcend.common.ErrorCode;
import com.zxr.aiojbakcend.common.ResultUtils;
import com.zxr.aiojbakcend.constant.JWTConstant;
import com.zxr.aiojbakcend.constant.UserConstant;
import com.zxr.aiojbakcend.enums.RoleCode;
import com.zxr.aiojbakcend.exception.BusinessException;
import com.zxr.aiojbakcend.mapper.RoleMapper;
import com.zxr.aiojbakcend.mapper.UserMapper;
import com.zxr.aiojbakcend.mapper.UserRoleMapper;
import com.zxr.aiojbakcend.model.domain.Auth;
import com.zxr.aiojbakcend.model.domain.Role;
import com.zxr.aiojbakcend.model.domain.User;
import com.zxr.aiojbakcend.model.dto.user.request.UserLoginRequest;
import com.zxr.aiojbakcend.model.dto.user.request.UserRegisterRequest;
import com.zxr.aiojbakcend.model.vo.LoginUserVO;
import com.zxr.aiojbakcend.security.entity.LoginUser;
import com.zxr.aiojbakcend.service.UserService;
import com.zxr.aiojbakcend.utils.VerificationToolUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.xmlbeans.impl.xb.xsdschema.Attribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    @Autowired
    private UserRoleMapper userRoleMapper;

    /**
     * 用户登录
     * @param userLoginRequest 用户登录请求
     * @return 登录成功返回token
     */
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

        log.debug("序列化后的对象" + loginUserJson);

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

    /**
     * 用户登出
     * @return 是否登出成功
     */
    @Override
    public boolean logout() {
        // 获取当前用户
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        // 获取用户id
        Integer id = loginUser.getUser().getId();
        if (loginUser != null) {
            // 删除redis中的用户信息
            stringRedisTemplate.delete(UserConstant.USER_LOGIN_STATE + id);
            return true;
        }

        return false;
    }

    /**
     * 用户注册
     * @param userRegisterRequest 用户注册请求
     * @return 是否注册成功
     */
    @Override
    public boolean register(UserRegisterRequest userRegisterRequest) {
        String phone = userRegisterRequest.getPhone();
        String password = userRegisterRequest.getPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(phone, password, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 校验手机号
        if (!VerificationToolUtils.checkMobilePhone(phone)) {
            throw new BusinessException(ErrorCode.PHONE_ERROR);
        }
        // 校验密码
        if (password.length() < 6 || password.length() > 16) {
            throw new BusinessException(ErrorCode.PASSWORD_LENGTH_ERROR);
        }
        // 校验密码是否一致
        if (!password.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.CHECK_PASSWORD_ERROR);
        }

        //根据手机号查询用户
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", phone);
        Long count = this.baseMapper.selectCount(queryWrapper);
        if(count > 0){
            throw new BusinessException(ErrorCode.USER_EXIST);
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        // 创建用户
        User newUser = new User();
        newUser.setPhone(phone);
        newUser.setPassword(bCryptPasswordEncoder.encode(password));
        boolean save = this.save(newUser);
        if(!save){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        // 设置用户角色
        Integer id = newUser.getId();
        userRoleMapper.insertUserRole(id, RoleCode.DEFAULT_USER.getRoleCode());
        log.debug("注册成功");
        return true;
    }

    @Override
    public LoginUserVO getLoginUser(HttpServletRequest request) {
        // 1.从SecurityContextHolder中获取Authentication对象
        // 2.从Authentication对象中获取Principal对象
        // 3.将Principal对象转换为LoginUser对象
        // 4.将LoginUser对象转换为LoginUserVO对象
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        LoginUserVO loginUserVO = new LoginUserVO();
        BeanUtil.copyProperties(loginUser.getUser(),loginUserVO);
        // 设置权限返回
        loginUserVO.setPermissions(loginUser.getPermissions());
        // 设置角色返回
        loginUserVO.setRole(loginUser.getRole());
        return loginUserVO;
    }
}




