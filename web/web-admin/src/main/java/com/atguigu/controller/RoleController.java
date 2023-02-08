package com.atguigu.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.PermissionService;
import com.atguigu.RoleService;
import com.atguigu.entity.Role;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {

    public static final String SUCCESS_PAGE = "common/successPage";
    private final static String LIST_ACTION = "redirect:/role";

    @Reference
    private RoleService roleService;

    @Reference
    private PermissionService permissionService;

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
    @PreAuthorize("hasAnyAuthority('role.show')")
    @RequestMapping
    public String index(Map map, HttpServletRequest request) {
        // 获取请求参数
        Map<String, Object> filters = getFilters(request);
        map.put("filters", filters);
        // 调用RoleService 中分页及查询方法
        PageInfo<Role> pageInfo = roleService.findPage(filters);

        map.put("page", pageInfo);
        return "role/index";
    }


    // 去添加角色的页面
    @PreAuthorize("hasAnyAuthority('role.create')")
    @RequestMapping("/create")
    public String goAddPage() {
        return "role/create";
    }

    /**
     * @description: 添加角色
     * @author: ChenXW
     * @date: 2023/2/3 21:45
     */
    @PreAuthorize("hasAnyAuthority('role.create')")
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
    @PreAuthorize("hasAnyAuthority('role.delete')") // 此时只有delete权限的时候才能调用以下方法
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
    @PreAuthorize("hasAnyAuthority('role.edit')")
    @RequestMapping("/edit/{roleId}")
    public String goEditPage(@PathVariable("roleId") Long roleId, Map map) {
        Role role = roleService.getById(roleId);

        map.put("role", role);

        return "role/edit";
    }


    // 更新角色
    @PreAuthorize("hasAnyAuthority('role.edit')")
    @RequestMapping("/update")
    public String update(Role role) {
        roleService.update(role);

        return SUCCESS_PAGE;
    }

    /**
     * @description: 去分配权限的页面
     * @author: ChenXW
     * @date: 2023/2/7 17:56
     */
    @PreAuthorize("hasAnyAuthority('role.assgin')")
    @RequestMapping("/assginShow/{roleId}")
    public String goAssignShowPage(@PathVariable("roleId") Long roleId, Map map) {
        map.put("roleId", roleId);
        // 根据角色id获取权限
        List<Map<String, Object>> zNodes = permissionService.findPermissionByRoleId(roleId);
        // 将权限放到requests域中
        map.put("zNodes", zNodes);

        return "role/assginShow";

    }


    /**
     * @description: 分配权限
     * @author: ChenXW
     * @date: 2023/2/7 18:40
     */
    @PreAuthorize("hasAnyAuthority('role.assgin')")
    @RequestMapping("/assignPermission")
    public String assignPermission(@RequestParam("roleId") Long roleId, @RequestParam("permissionIds") Long[] permissionIds) {
        // 分配权限的方法
        permissionService.assignPermission(roleId, permissionIds);
        return "common/successPage";

    }


}
