package com.atguigu.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.CommunityService;
import com.atguigu.DictService;
import com.atguigu.entity.Community;
import com.atguigu.entity.Dict;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/community")
public class CommunityController extends BaseController {


    @Reference
    private CommunityService communityService;

    @Reference
    private DictService dictService;

    /**
     * @description: 分页查询及带查询的方法
     * @author: ChenXW
     * @date: 2023/2/5 11:39
     */
    @RequestMapping
    public String index(Map map, HttpServletRequest request) {
        // 获取请求参数
        Map<String, Object> filters = getFilters(request);

        map.put("filters", filters);

        PageInfo<Community> pageInfo = communityService.findPage(filters);

        map.put("page", pageInfo);

        // 根据编码获取北京所有的区
        List<Dict> areaList = dictService.findListByDictCode("beijing");

        // 将北京所有的区域放在request域中
        map.put("areaList", areaList);
        return "community/index";
    }


    /**
     * @description: 去添加小区的页面
     * @author: ChenXW
     * @date: 2023/2/5 13:11
     */
    @RequestMapping("/create")
    public String goAddPage(Map map) {
        // 根据编码获取北京所有的区
        List<Dict> areaList = dictService.findListByDictCode("beijing");

        // 将北京所有的区域放在request域中
        map.put("areaList", areaList);

        return "community/create";
    }

    /**
     * @description: 添加小区
     * @author: ChenXW
     * @date: 2023/2/5 13:15
     */
    @RequestMapping("/save")
    public String save(Community community) {
        // 调用CommunityService中添加的方法
        communityService.insert(community);
        // 去成功页面
        return "common/successPage";
    }


    /**
     * @description: 修改小区的页面
     * @author: ChenXW
     * @date: 2023/2/5 13:42
     */
    @RequestMapping("/edit/{id}")
    public String goEditPage(@PathVariable("id") Long id, Map map) {
        // 根据编码获取北京所有的区
        List<Dict> areaList = dictService.findListByDictCode("beijing");

        // 将北京所有的区域放在request域中
        map.put("areaList", areaList);

        Community community = communityService.getById(id);

        map.put("community", community);
        return "community/edit";
    }

    /**
     * @description: 更新
     * @author: ChenXW
     * @date: 2023/2/5 13:53
     */
    @RequestMapping("/update")
    public String update(Community community){
        communityService.update(community);
        return "common/successPage";
    }


    /**
     * @description: 删除
     * @author: ChenXW
     * @date: 2023/2/5 13:54
     */
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        communityService.delete(id);
        return "redirect:/community";
    }

}
