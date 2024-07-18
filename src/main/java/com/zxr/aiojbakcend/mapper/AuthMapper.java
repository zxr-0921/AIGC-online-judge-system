package com.zxr.aiojbakcend.mapper;

import com.zxr.aiojbakcend.model.domain.Auth;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author zxr0921
* @description 针对表【auth(权限表)】的数据库操作Mapper
* @createDate 2024-07-18 12:14:45
* @Entity com.zxr.aiojbakcend.model.domain.Auth
*/
public interface AuthMapper extends BaseMapper<Auth> {

    List<String> selectPermissionByUserId(Integer userId);
}




