package com.atguigu.service.impl;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.DictService;
import com.atguigu.UserFollowService;
import com.atguigu.dao.BaseDao;
import com.atguigu.dao.UserFollowDao;
import com.atguigu.entity.UserFollow;
import com.atguigu.result.Result;
import com.atguigu.service.BaseService;
import com.atguigu.vo.UserFollowVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Service(interfaceClass = UserFollowService.class)
@Transactional
public class UserFollowServiceImpl extends BaseServiceImpl<UserFollow> implements UserFollowService {

    @Autowired
    private UserFollowDao userFollowDao;

    @Reference
    private DictService dictService;

    @Reference
    private UserFollowService userFollowService;

    @Override
    protected BaseDao<UserFollow> getEntityDao() {
        return userFollowDao;
    }

    @Override
    public void follow(Long id, Long houseId) {
        // 创建UserFollow对象
        UserFollow userFollow = new UserFollow();
        userFollow.setUserId(id);
        userFollow.setHouseId(houseId);
        // 调用添加方法
        userFollowDao.insert(userFollow);


    }

//    @Override
//    public Boolean isFollowed(Long userId, Long houseId) {
//        // 调用方法查询是否关注该房源
//        Integer count = userFollowDao.getCountByUserIdAndHouseId(userId, houseId);
//
//        if (count > 0) {
//            return true;
//        } else {
//            return false;
//        }
//
//    }

    @Override
    public Boolean isFollowed(Long userId, Long houseId) {
        Integer count = userFollowDao.countByUserIdAndHouserId(userId, houseId);
        if (count.intValue() == 0) {
            return false;
        }
        return true;
    }

    @Override
    public PageInfo<UserFollowVo> findPageList(Integer pageNum, Integer pageSize, Long userId) {
        // 开启分页
        PageHelper.startPage(pageNum, pageSize);
        Page<UserFollowVo> page = userFollowDao.findPageList(userId);

        for (UserFollowVo userFollowVo : page) {
            // 获取房屋的类型
            String houseTypeName = dictService.getNameById(userFollowVo.getHouseTypeId());
            // 获取楼层
            String floorName = dictService.getNameById(userFollowVo.getFloorId());
            // 获取朝向
            String directionName = dictService.getNameById(userFollowVo.getDirectionId());
            userFollowVo.setHouseTypeName(houseTypeName);
            userFollowVo.setFloorName(floorName);
            userFollowVo.setDirectionName(directionName);
        }
        return new PageInfo<UserFollowVo>(page, 5);
    }

    @Override
    public void cancelFollowed(Long id) {
        userFollowDao.delete(id);

    }


}
