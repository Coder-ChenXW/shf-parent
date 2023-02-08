package com.atguigu.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.AdminService;
import com.atguigu.PermissionService;
import com.atguigu.entity.Admin;
import com.atguigu.entity.Permission;
import org.springframework.security.config.annotation.SecurityConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;


@Controller
public class IndexController {

    @Reference
    private AdminService adminService;

    @Reference
    private PermissionService permissionService;

    // 去首页
//    @RequestMapping("/")
//    public String index() {
//        return "frame/index";
//    }

    @RequestMapping("/")
    public String index(Map map) {
        // 设置默认的用户id
//        Long userId = 1L;
        // 查询方法
//        Admin admin = adminService.getById(userId);
        // 通过SpringSecurity获取User对象
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // 调用adminService中根据用户名获取admin对象的方法
        Admin admin = adminService.getAdminByUsername(user.getUsername());
        // 获取用户权限菜单
        List<Permission> permissionList = permissionService.getMenuPermissionsByAdminId(admin.getId());
        // 将用户和用户的权限菜单放到request域中
        map.put("admin", admin);
        map.put("permissionList", permissionList);
        return "frame/index";
    }

    // 去主页面
    @RequestMapping("/main")
    public String main() {
        return "frame/main";
    }

    /**
     * @description: 去登录页面
     * @author: ChenXW
     * @date: 2023/2/7 21:46
     */
    @RequestMapping("login")
    public String login() {
        return "frame/login.html";
    }


    /**
     * @description: 去没有权限的提示页面
     * @author: ChenXW
     * @date: 2023/2/8 17:08
     */
    @RequestMapping("/auth")
    public String auth() {
        return "frame/auth";
    }


}
