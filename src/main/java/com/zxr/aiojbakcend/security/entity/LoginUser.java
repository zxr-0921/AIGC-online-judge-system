package com.zxr.aiojbakcend.security.entity;

import com.zxr.aiojbakcend.model.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser implements UserDetails {

    // 用户信息
    private User user;
    // 存储权限信息
    private List<String> permissions;
    // 存储角色信息
    private String role;

    public LoginUser(User user, List<String> permissions, String role) {
        this.user = user;
        this.permissions = permissions;
        this.role = role;
    }

    // todo: 2021/8/3 为什么加上这个注解
//    @JSONField(serialize = false)
    private List<SimpleGrantedAuthority> authorities;
    /**
     * 查询权限信息封装到LoginUser中
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(authorities!=null){
            return authorities;
        }
        authorities= permissions.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getPhone();
    }
}
