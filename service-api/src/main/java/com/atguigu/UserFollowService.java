package com.atguigu;


import com.atguigu.entity.UserFollow;
import com.atguigu.service.BaseService;
import com.atguigu.vo.UserFollowVo;
import com.github.pagehelper.PageInfo;

public interface UserFollowService extends BaseService<UserFollow> {

    // 关注房源
    void follow(Long id, Long houseId);

    // 查询是否关注该房源
    Boolean isFollowed(Long userId, Long houseId);

    // 分页查询我的关注
    PageInfo<UserFollowVo> findPageList(Integer pageNum, Integer pageSize, Long id);

    // 取消关注房源
    void cancelFollowed(Long id);
}
