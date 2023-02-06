package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.*;
import com.atguigu.entity.Community;
import com.atguigu.entity.House;
import com.atguigu.entity.HouseBroker;
import com.atguigu.entity.HouseImage;
import com.atguigu.result.Result;
import com.atguigu.vo.HouseQueryVo;
import com.atguigu.vo.HouseVo;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/house")
@CrossOrigin
@SuppressWarnings({"unchecked", "rawtypes"})
public class HouseController {

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

    /**
     * @description: 分页及带条件查询的方法
     * @author: ChenXW
     * @date: 2023/2/6 15:10
     */
    @PostMapping(value = "/list/{pageNum}/{pageSize}")
    public Result findListPage(@RequestBody HouseQueryVo houseQueryVo,
                               @PathVariable Integer pageNum,
                               @PathVariable Integer pageSize) {
        PageInfo<HouseVo> pageInfo = houseService.findListPage(pageNum, pageSize, houseQueryVo);
        return Result.ok(pageInfo);
    }

    /**
     * @description: 查看房源详情
     * @author: ChenXW
     * @date: 2023/2/6 17:39
     */
    @RequestMapping("/info/{id}")
    public Result info(@PathVariable("id") Long id) {
        // 调用查询房源的方法
        House house = houseService.getById(id);
        // 获取小区信息
        Community community = communityService.getById(house.getCommunityId());
        // 获取房源的图片
        List<HouseImage> houseImage1List = houseImageService.getHouseImagesByHouseIdAndType(id, 1);
        // 获取所有的经纪人
        List<HouseBroker> houseBrokerList = houseBrokerService.getHouseBrokersByHouseId(id);

        Map map = new HashMap<>();

        map.put("house", house);
        map.put("community", community);
        map.put("houseImage1List", houseImage1List);
        map.put("houseBrokerList", houseBrokerList);

        // 设置默认没有关注房源
        map.put("isFollow", false);
        return Result.ok(map);
    }

}
