package com.zxr.aiojbakcend.security.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zxr.aiojbakcend.common.ErrorCode;
import com.zxr.aiojbakcend.exception.BusinessException;
import com.zxr.aiojbakcend.mapper.AuthMapper;
import com.zxr.aiojbakcend.mapper.RoleMapper;
import com.zxr.aiojbakcend.mapper.UserMapper;
import com.zxr.aiojbakcend.security.entity.LoginUser;
import com.zxr.aiojbakcend.model.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 重写UserDetailsService接口的方法
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AuthMapper authMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        // 根据手机号查询用户
        LambdaQueryWrapper lambdaQueryWrapper = new LambdaQueryWrapper<User>().eq(User::getPhone, phone);
        User user = userMapper.selectOne(lambdaQueryWrapper);
        // 如果用户不存在，抛出用户不存在异常
        if (Objects.isNull(user)) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }
        // 根据用户id查询用户权限
        List<String> permission = authMapper.selectPermissionByUserId(user.getId());
        // 查询用户角色
        String role = roleMapper.selectRoleByUserId(user.getId());
        System.out.println("role = " + role);
        // UserDetails接口的实现类
        return new LoginUser(user,permission,role);
    }
}


