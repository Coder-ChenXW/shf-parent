package com.atguigu.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.AdminService;
import com.atguigu.HouseBrokerService;
import com.atguigu.HouseService;
import com.atguigu.entity.Admin;
import com.atguigu.entity.HouseBroker;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/houseBroker")
public class HouseBrokerController {

    @Reference
    private AdminService adminService;

    @Reference
    private HouseBrokerService houseBrokerService;

    /**
     * @description: 去添加经纪人的页面
     * @author: ChenXW
     * @date: 2023/2/6 0:00
     */
    @RequestMapping("/create")
    public String goAddPage(@RequestParam("houseId") Long houseId, Map map) {

        // 将房源的id放到request域中
        map.put("houseId", houseId);

        List<Admin> adminList = adminService.findAll();

        map.put("adminList", adminList);

        return "houseBroker/create";
    }


    /**
     * @description: 保存经纪人
     * @author: ChenXW
     * @date: 2023/2/6 0:11
     */
    @RequestMapping("/save")
    public String save(HouseBroker houseBroker) {
        // 根据经纪人id查询经纪人信息
        Admin admin = adminService.getById(houseBroker.getBrokerId());
        houseBroker.setBrokerHeadUrl(admin.getHeadUrl());
        houseBroker.setBrokerName(admin.getName());

        // 调用HouseBrokerService中保存的方法
        houseBrokerService.insert(houseBroker);

        return "common/successPage";

    }

    /**
     * @description: 去修改经纪人的页面
     * @author: ChenXW
     * @date: 2023/2/6 0:23
     */
    @RequestMapping("/edit/{id}")
    public String goEditPage(@PathVariable("id") Long id, Map map) {
        HouseBroker broker = houseBrokerService.getById(id);
        // 将经纪人放到request域中
        map.put("houseBroker", broker);

        List<Admin> adminList = adminService.findAll();

        map.put("adminList", adminList);

        return "houseBroker/edit";
    }

    /**
     * @description: 更新经纪人
     * @author: ChenXW
     * @date: 2023/2/6 0:28
     */
    @RequestMapping("/update")
    public String update(HouseBroker houseBroker) {

        Admin admin = adminService.getById(houseBroker.getBrokerId());
        houseBroker.setBrokerHeadUrl(admin.getHeadUrl());
        houseBroker.setBrokerName(admin.getName());

        // 更新方法
        houseBrokerService.update(houseBroker);
        return "common/successPage";

    }

    /**
     * @description: 删除经纪人
     * @author: ChenXW
     * @date: 2023/2/6 0:30
     */
    @RequestMapping("/delete/{houseId}/{brokerId}")
    public String delete(@PathVariable("houseId") Long houseId, @PathVariable("brokerId") Long brokerId) {
        // 调用HouseBrokerService中的删除方法
        houseBrokerService.delete(brokerId);
        // 重定向到查询详情的方法
        return "redirect:/house/" + houseId;
    }


}
