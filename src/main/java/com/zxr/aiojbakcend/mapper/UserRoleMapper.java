package com.zxr.aiojbakcend.mapper;

import com.zxr.aiojbakcend.model.domain.UserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;

/**
* @author zxr0921
* @description 针对表【user_role(用户角色表)】的数据库操作Mapper
* @createDate 2024-07-18 12:14:58
* @Entity com.zxr.aiojbakcend.model.domain.UserRole
*/
public interface UserRoleMapper extends BaseMapper<UserRole> {

    // 绑定用户角色
    @Insert("insert into user_role(uid, role_id) values (#{userId}, #{roleId})")
    int insertUserRole(Integer userId, Integer roleId);

}




