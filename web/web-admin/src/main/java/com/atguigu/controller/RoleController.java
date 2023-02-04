package com.atguigu.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.RoleService;
import com.atguigu.entity.Role;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {

    public static final String SUCCESS_PAGE = "common/successPage";
    private final static String LIST_ACTION = "redirect:/role";

    @Reference
    private RoleService roleService;

//    @RequestMapping
//    public String index(Map map) {
//        // 调用RoleService中获取所有角色的方法
//        List<Role> roleList = roleService.findAll();
//
//        map.put("list", roleList);
//
//        return "role/index";
//    }

    /**
     * @description: 分页及查询
     * @author: ChenXW
     * @date: 2023/2/4 10:46
     */
    @RequestMapping
    public String index(Map map, HttpServletRequest request){
        // 获取请求参数
        Map<String, Object> filters = getFilters(request);
        map.put("filters",filters);
        // 调用RoleService 中分页及查询方法
        PageInfo<Role> pageInfo = roleService.findPage(filters);

        map.put("page",pageInfo);
        return "role/index";
    }


    // 去添加角色的页面
    @RequestMapping("/create")
    public String goAddPage() {
        return "role/create";
    }

    /**
     * @description: 添加角色
     * @author: ChenXW
     * @date: 2023/2/3 21:45
     */
    @RequestMapping("/save")
    public String save(Role role) {
        // 调用RoleService中添加的方法
        roleService.insert(role);
//        return "redirect:/role";
        // 去common下的成功页面
        return SUCCESS_PAGE;
    }


    /**
     * @description: 删除角色
     * @author: ChenXW
     * @date: 2023/2/3 22:44
     */
    @RequestMapping("/delete/{roleId}")
    public String delete(@PathVariable("roleId") Long roleId) {
        roleService.delete(roleId);
        return LIST_ACTION;
    }


    /**
     * @description: 去修改页面的方法
     * @author: ChenXW
     * @date: 2023/2/3 22:53
     */
    @RequestMapping("/edit/{roleId}")
    public String goEditPage(@PathVariable("roleId") Long roleId, Map map) {
        Role role = roleService.getById(roleId);

        map.put("role", role);

        return "role/edit";
    }


    // 更新角色
    @RequestMapping("/update")
    public String update(Role role) {
        roleService.update(role);

        return SUCCESS_PAGE;
    }




}
