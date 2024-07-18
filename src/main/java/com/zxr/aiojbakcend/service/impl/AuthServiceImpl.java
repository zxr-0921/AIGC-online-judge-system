package com.zxr.aiojbakcend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxr.aiojbakcend.model.domain.Auth;
import com.zxr.aiojbakcend.service.AuthService;
import com.zxr.aiojbakcend.mapper.AuthMapper;
import org.springframework.stereotype.Service;

/**
* @author zxr0921
* @description 针对表【auth(权限表)】的数据库操作Service实现
* @createDate 2024-07-18 12:14:45
*/
@Service
public class AuthServiceImpl extends ServiceImpl<AuthMapper, Auth>
    implements AuthService{

}




