package com.zxr.aiojbakcend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxr.aiojbakcend.model.domain.Role;
import com.zxr.aiojbakcend.service.RoleService;
import com.zxr.aiojbakcend.mapper.RoleMapper;
import org.springframework.stereotype.Service;

/**
* @author zxr0921
* @description 针对表【role(角色表)】的数据库操作Service实现
* @createDate 2024-07-18 12:14:40
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService{

}




