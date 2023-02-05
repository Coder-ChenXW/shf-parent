package com.atguigu.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.DictService;
import com.atguigu.entity.Dict;
import com.atguigu.result.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/dict")
public class DictController {

    @Reference
    private DictService dictService;

    /**
     * @description: 去展示数据字典的页面
     * @author: ChenXW
     * @date: 2023/2/5 10:20
     */
    @RequestMapping
    public String index() {
        return "dict/index";
    }


    /**
     * @description:
     * @author: ChenXW
     * @date: 2023/2/5 10:33
     */
    @ResponseBody
    @RequestMapping("/findZnodes")
    public Result findZnodes(@RequestParam(value = "id", defaultValue = "0") Long id) {
        // 调用调用DictService查询数据字典中的数据
        List<Map<String, Object>> zNodes = dictService.findZnodes(id);
        return Result.ok(zNodes);

    }


    /**
     * @description: 根据父id获取所有子节点
     * @author: ChenXW
     * @date: 2023/2/5 13:02
     */
    @ResponseBody
    @RequestMapping("/findListByParentId/{areaId}")
    public Result findListByParentId(@PathVariable("areaId") Long areaId) {
        // 调用DictService根据父Id查询所有子节点的方法
        List<Dict> listByParentId = dictService.findListByParentId(areaId);
        return Result.ok(listByParentId);

    }

}
