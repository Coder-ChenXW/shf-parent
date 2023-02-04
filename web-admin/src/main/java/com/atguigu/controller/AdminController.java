package com.atguigu.controller;

import com.atguigu.entity.Admin;
import com.atguigu.service.AdminService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {

    @Autowired
    private AdminService adminService;

    /**
     * @description: 分页及带条件查询
     * @author: ChenXW
     * @date: 2023/2/4 12:01
     */
    @RequestMapping
    public String findPage(Map map, HttpServletRequest request) {
        // 获取请求参数
        Map<String, Object> filters = getFilters(request);

        // 讲filters放到request域中
        map.put("filters", filters);

        // 调用AdminService中分页的方法
        PageInfo<Admin> pageInfo = adminService.findPage(filters);
        // 将pageInfo 对象放到request域中
        map.put("page", pageInfo);

        return "admin/index";
    }

    /**
     * @description: 去添加用户的页面
     * @author: ChenXW
     * @date: 2023/2/4 13:13
     */
    @RequestMapping("/create")
    public String goAddPage() {
        return "admin/create";
    }

    /**
     * @description: 保存用户
     * @author: ChenXW
     * @date: 2023/2/4 13:16
     */
    @RequestMapping("/save")
    public String save(Admin admin) {
        // 调用AdminService中保存的方法
        adminService.insert(admin);
        return "common/successPage";
    }

    /**
     * @description: 删除用户
     * @author: ChenXW
     * @date: 2023/2/4 13:20
     */
    @RequestMapping("/delete/{adminId}")
    public String delete(@PathVariable("adminId") Long adminId) {
        // 调用AdminService中删除的方法
        adminService.delete(adminId);
        // 重定向
        return "redirect:/admin";
    }

    /**
     * @description: 去更新的页面
     * @author: ChenXW
     * @date: 2023/2/4 13:22
     */
    @RequestMapping("/edit/{adminId}")
    public String goEditPage(@PathVariable("adminId") Long adminId, Map map) {
        // 调用AdminService中根据id查询一个对象的方法
        Admin admin = adminService.getById(adminId);

        map.put("admin", admin);
        return "admin/edit";
    }


    /**
     * @description: 更新用户
     * @author: ChenXW
     * @date: 2023/2/4 13:26
     */
    @RequestMapping("/update")
    public String update(Admin admin) {
        // 调用AdminService中更新的方法
        adminService.update(admin);
        return "common/successPage";
    }

}
