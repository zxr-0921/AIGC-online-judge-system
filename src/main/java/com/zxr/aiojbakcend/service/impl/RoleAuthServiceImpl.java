package com.zxr.aiojbakcend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxr.aiojbakcend.model.domain.RoleAuth;
import com.zxr.aiojbakcend.service.RoleAuthService;
import com.zxr.aiojbakcend.mapper.RoleAuthMapper;
import org.springframework.stereotype.Service;

/**
* @author zxr0921
* @description 针对表【role_auth(角色权限表)】的数据库操作Service实现
* @createDate 2024-07-18 12:14:54
*/
@Service
public class RoleAuthServiceImpl extends ServiceImpl<RoleAuthMapper, RoleAuth>
    implements RoleAuthService{

}




