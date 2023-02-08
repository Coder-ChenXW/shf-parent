package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.AdminService;
import com.atguigu.RoleService;
import com.atguigu.entity.Admin;
import com.atguigu.util.QiniuUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;


@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {

    @Reference
    private AdminService adminService;

    @Reference
    private RoleService roleService;

    // 注入密码加密器
    @Autowired
    private PasswordEncoder passwordEncoder;

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
        // 对Admin对象中的密码进行加密
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
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

    /**
     * @description: 上传头像的页面
     * @author: ChenXW
     * @date: 2023/2/6 12:08
     */
    @RequestMapping("/uploadShow/{id}")
    public String goUploadPage(@PathVariable("id") Long id, Map map) {
        map.put("id", id);
        return "admin/upload";
    }

    /**
     * @description: 上传头像
     * @author: ChenXW
     * @date: 2023/2/6 12:12
     */
    @RequestMapping("/upload/{id}")
    public String upload(@PathVariable("id") Long id, MultipartFile file) {

        try {
            Admin admin = adminService.getById(id);
            // 获取字节数组
            byte[] bytes = file.getBytes();
            // 上传到七牛云
            String fileName = UUID.randomUUID().toString();
            QiniuUtil.upload2Qiniu(bytes, fileName);
            // 给用户设置头像地址
            admin.setHeadUrl("http://rpmyvhai5.hn-bkt.clouddn.com/" + fileName);
            // 调用更新
            adminService.update(admin);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "common/successPage";
    }


    /**
     * @description: 去分配角色的页面
     * @author: ChenXW
     * @date: 2023/2/7 17:12
     */
    @RequestMapping("/assignShow/{adminId}")
    public String goAssignShowPage(@PathVariable("adminId") Long adminId, ModelMap modelMap) {
        // 将用户id放到request域中
        modelMap.addAttribute("adminId", adminId);
        // 根据用户id查询用户角色的方法
        Map<String, Object> rolesByAdminId = roleService.findRolesByAdminId(adminId);
        // 将map放倒request域中
        modelMap.addAllAttributes(rolesByAdminId);

        return "admin/assignShow";
    }


    /**
     * @description: 分配角色
     * @author: ChenXW
     * @date: 2023/2/7 17:43
     */
    @RequestMapping("/assignRole")
    public String assignRole(Long adminId, Long[] roleIds) {
        // 分配角色的方法
        roleService.assignRole(adminId, roleIds);
        return "common/successPage";

    }


}
