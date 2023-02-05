package com.atguigu.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.*;
import com.atguigu.entity.*;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/house")
public class HouseController extends BaseController {

    @Reference
    private HouseService houseService;

    @Reference
    private CommunityService communityService;

    @Reference
    private DictService dictService;

    @Reference
    private HouseImageService houseImageService;

    @Reference
    private HouseBrokerService houseBrokerService;

    @Reference
    private HouseUserService houseUserService;

    /**
     * @description: 分页及带查询的方法
     * @author: ChenXW
     * @date: 2023/2/5 18:33
     */
    @RequestMapping
    public String index(Map map, HttpServletRequest request) {
        // 获取请求参数
        Map<String, Object> filters = getFilters(request);
        map.put("filters", filters);
        // 调用HouseService中分页及带条件查询
        PageInfo<House> info = houseService.findPage(filters);
        map.put("page", info);

        setRequestAttribute(map);

        return "house/index";
    }


    /**
     * @description: 添加去房源的页面
     * @author: ChenXW
     * @date: 2023/2/5 19:54
     */
    @RequestMapping("/create")
    public String goAddPage(Map map) {
        setRequestAttribute(map);
        return "house/create";
    }


    /**
     * @description: 添加房源
     * @author: ChenXW
     * @date: 2023/2/5 19:58
     */
    @RequestMapping("/save")
    public String save(House house) {
        // 调用houseService中添加的方法
        houseService.insert(house);
        return "common/successPage";
    }


    /**
     * @description: 去修改的页面
     * @author: ChenXW
     * @date: 2023/2/5 20:08
     */
    @RequestMapping("/edit/{id}")
    public String goEditPage(@PathVariable("id") Long id, Map map) {
        House house = houseService.getById(id);
        // 将house放到request
        map.put("house", house);

        // 将小区和数据字典的数据放到request域中
        setRequestAttribute(map);

        return "house/edit";

    }


    /**
     * @description: 更新
     * @author: ChenXW
     * @date: 2023/2/5 20:33
     */
    @RequestMapping("/update")
    public String update(House house) {
        houseService.update(house);
        return "common/successPage";
    }

    /**
     * @description: 删除
     * @author: ChenXW
     * @date: 2023/2/5 20:35
     */
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        // 调用删除的方法
        houseService.delete(id);
        return "redirect:/house";
    }

    /**
     * @description: 将所有小区及数据字典中数据放到request域中的方法
     * @author: ChenXW
     * @date: 2023/2/5 20:44
     */
    public void setRequestAttribute(Map map) {
        // 获取所以的小区
        List<Community> communityList = communityService.findAll();
        // 获取所有户型
        List<Dict> houseTypeList = dictService.findListByDictCode("houseType");
        // 获取楼层
        List<Dict> floorList = dictService.findListByDictCode("floor");
        // 获取建筑结构
        List<Dict> buildStructureList = dictService.findListByDictCode("buildStructure");
        // 获取朝向
        List<Dict> directionList = dictService.findListByDictCode("direction");
        // 获取装修情况
        List<Dict> decorationList = dictService.findListByDictCode("decoration");
        // 获取房屋用途
        List<Dict> houseUseList = dictService.findListByDictCode("houseUse");
        // 将以上信息放到request域中
        map.put("communityList", communityList);
        map.put("houseTypeList", houseTypeList);
        map.put("floorList", floorList);
        map.put("buildStructureList", buildStructureList);
        map.put("directionList", directionList);
        map.put("decorationList", decorationList);
        map.put("houseUseList", houseUseList);
    }

    /**
     * @description: 发布和取消发布
     * @author: ChenXW
     * @date: 2023/2/5 20:49
     */
    @RequestMapping("/publish/{houseId}/{status}")
    public String publish(@PathVariable("houseId") Long houseId, @PathVariable("status") Integer status) {
        // 调用发布或取消方法
        houseService.publish(houseId, status);
        // 重定向到查询房源的方法
        return "redirect:/house";
    }

    /**
     * @description: 查询房源详情
     * @author: ChenXW
     * @date: 2023/2/5 22:20
     */
    @RequestMapping("/{houseId}")
    public String show(@PathVariable("houseId") Long houseId, Map map) {
        // 调用HouseService中根据id查询房源的方法
        House house = houseService.getById(houseId);
        // 将房源当道request域中
        map.put("house", house);
        // 调用CommunityService中根据小区id查询小区的方法
        Community community = communityService.getById(house.getCommunityId());
        // 将小区信息放到request域中
        map.put("community", community);
        // 查询房源图片
        List<HouseImage> houseImage1List = houseImageService.getHouseImagesByHouseIdAndType(houseId, 1);
        // 查询房产图片
        List<HouseImage> houseImage2List = houseImageService.getHouseImagesByHouseIdAndType(houseId, 2);
        // 查询经纪人
        List<HouseBroker> houseBrokerList = houseBrokerService.getHouseBrokersByHouseId(houseId);
        // 查询房东
        List<HouseUser> houseUserList= houseUserService.getHouseUsersByHouseId(houseId);
        // 放到request域中
        map.put("houseImage1List",houseImage1List);
        map.put("houseImage2List",houseImage2List);
        map.put("houseBrokerList",houseBrokerList);
        map.put("houseUserList",houseUserList);
        return "house/show";
    }

}
