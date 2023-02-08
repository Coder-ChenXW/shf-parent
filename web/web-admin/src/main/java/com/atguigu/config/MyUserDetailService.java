package com.atguigu.config;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.AdminService;
import com.atguigu.PermissionService;
import com.atguigu.entity.Admin;
import com.github.pagehelper.StringUtil;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;


@Component
public class MyUserDetailService implements UserDetailsService {

    @Reference
    private AdminService adminService;

    @Reference
    private PermissionService permissionService;

    // 登录时SpringSecurity会自动调用该方法
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 调用adminService
        Admin admin = adminService.getAdminByUsername(username);
        if (admin == null) {
            throw new UsernameNotFoundException("用户不存在!");
        }

        // 调用PermissionService中获取当前用户权限的方法
        List<String> permissionCodes = permissionService.getPermissionCodeByAdminId(admin.getId());
        // 创建一个用于授权的集合
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        // 遍历得到每一个权限码
        for (String permissionCode : permissionCodes) {
            if (!StringUtils.isEmpty(permissionCode)){
                SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(permissionCode);
                // 放入权限集合中
                grantedAuthorities.add(simpleGrantedAuthority);
            }
        }
        // 给用户授权
//        return new User(username, admin.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList(""));
        return new User(username, admin.getPassword(), grantedAuthorities);
    }


}
