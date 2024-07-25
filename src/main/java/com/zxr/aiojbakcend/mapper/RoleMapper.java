package com.zxr.aiojbakcend.mapper;

import com.zxr.aiojbakcend.model.domain.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
* @author zxr0921
* @description 针对表【role(角色表)】的数据库操作Mapper
* @createDate 2024-07-18 12:14:40
* @Entity com.zxr.aiojbakcend.model.domain.Role
*/
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据用户id查询用户角色
     * @param id
     * @return
     */
    @Select("select role from role where id in (select role_id from user_role where uid = #{id})")
    String selectRoleByUserId(Integer id);
}




