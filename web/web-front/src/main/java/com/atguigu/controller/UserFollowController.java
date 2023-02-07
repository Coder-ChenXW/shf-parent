package com.atguigu.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.UserFollowService;
import com.atguigu.entity.UserInfo;
import com.atguigu.result.Result;
import com.atguigu.vo.UserFollowVo;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;


@RestController
@RequestMapping("/userFollow")
public class UserFollowController {


    @Reference
    private UserFollowService userFollowService;

    /**
     * @description: 关注房源
     * @author: ChenXW
     * @date: 2023/2/7 11:33
     */
    @RequestMapping("/auth/follow/{houseId}")
    public Result follow(@PathVariable("houseId") Long houseId, HttpSession session) {
        // 获取UserInfo对象
        UserInfo userInfo = (UserInfo) session.getAttribute("user");
        // 调用关注房源的方法
        userFollowService.follow(userInfo.getId(), houseId);
        return Result.ok();
    }


    /**
     * @description: 查询我的关注
     * @author: ChenXW
     * @date: 2023/2/7 14:13
     */
    @RequestMapping("/auth/list/{pageNum}/{pageSize}")
    public Result myFollowed(@PathVariable("pageNum") Integer pageNum, @PathVariable("pageSize") Integer pageSize, HttpSession session) {
        UserInfo userInfo = (UserInfo) session.getAttribute("user");
        // 调用分页查询方法
        PageInfo<UserFollowVo> pageInfo = userFollowService.findPageList(pageNum, pageSize, userInfo.getId());

        return Result.ok(pageInfo );
    }


    /**
     * @description: 取消关注
     * @author: ChenXW
     * @date: 2023/2/7 15:06
     */
    @RequestMapping("/auth/cancelFollow/{id}")
    public Result cancelFollowed(@PathVariable("id") Long id) {
        // 调用UserFollowService中取消关注的方法
        userFollowService.cancelFollowed(id);
        return Result.ok();
    }

}
