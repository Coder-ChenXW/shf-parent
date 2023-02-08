package com.atguigu.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.HouseUserService;
import com.atguigu.entity.House;
import com.atguigu.entity.HouseUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;


@Controller
@RequestMapping("/houseUser")
public class HouseUserController {

    @Reference
    private HouseUserService houseUserService;

    /**
     * @description: 去添加页面
     * @author: ChenXW
     * @date: 2023/2/6 0:50
     */
    @RequestMapping("/create")
    public String goAddPage(@RequestParam("houseId") Long houseId, Map map) {
        // 将房源id放到request域中
        map.put("houseId", houseId);
        return "houseUser/create";
    }

    /**
     * @description: 添加保存
     * @author: ChenXW
     * @date: 2023/2/6 0:55
     */
    @RequestMapping("/save")
    public String save(HouseUser houseUser) {
        // 调用HouseUserService中添加的方法
        houseUserService.insert(houseUser);
        return "common/successPage";
    }


    /**
     * @description: 去修改的页面
     * @author: ChenXW
     * @date: 2023/2/6 0:56
     */
    @RequestMapping("/edit/{id}")
    public String goEditPage(@PathVariable("id") Long id, Map map) {
        // 调用HouseUserService中根据id查询的方法
        HouseUser houseUser = houseUserService.getById(id);
        map.put("houseUser", houseUser);
        return "houseUser/edit";
    }

    /**
     * @description: 更新
     * @author: ChenXW
     * @date: 2023/2/6 1:00
     */
    @RequestMapping("/update")
    public String update(HouseUser houseUser) {
        // 更新方法
        houseUserService.update(houseUser);
        return "common/successPage";
    }

    /**
     * @description: 删除
     * @author: ChenXW
     * @date: 2023/2/6 1:02
     */
    @RequestMapping("/delete/{houseId}/{houseUserId}")
    public String delete(@PathVariable("houseId") Long houseId, @PathVariable("houseUserId") Long houseUserId) {
        // 调用删除方法
        houseUserService.delete(houseUserId);

        // 重定向
        return "redirect:/house/"+houseId;
    }

}
