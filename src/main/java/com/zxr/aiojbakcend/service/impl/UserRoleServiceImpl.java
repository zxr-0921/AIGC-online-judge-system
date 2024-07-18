package com.zxr.aiojbakcend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxr.aiojbakcend.model.domain.UserRole;
import com.zxr.aiojbakcend.service.UserRoleService;
import com.zxr.aiojbakcend.mapper.UserRoleMapper;
import org.springframework.stereotype.Service;

/**
* @author zxr0921
* @description 针对表【user_role(用户角色表)】的数据库操作Service实现
* @createDate 2024-07-18 12:14:58
*/
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>
    implements UserRoleService{

}




