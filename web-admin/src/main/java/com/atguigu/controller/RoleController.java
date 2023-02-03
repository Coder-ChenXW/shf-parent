package com.atguigu.controller;


import com.atguigu.entity.Role;
import com.atguigu.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping
    public String index(Map map) {
        // 调用RoleService中获取所有角色的方法
        List<Role> roleList = roleService.findAll();

        map.put("list",roleList);

        return "role/index";
    }

}
