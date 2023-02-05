package com.atguigu.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.HouseService;
import com.atguigu.dao.BaseDao;
import com.atguigu.dao.DictDao;
import com.atguigu.dao.HouseDao;
import com.atguigu.entity.House;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@Service(interfaceClass = HouseService.class)
@Transactional
public class HouseServiceImpl extends BaseServiceImpl<House> implements HouseService {

    @Autowired
    private HouseDao houseDao;

    @Autowired
    private DictDao dictDao;

    @Override
    protected BaseDao<House> getEntityDao() {
        return houseDao;
    }

    @Override
    public void publish(Long houseId, Integer status) {
        // 创建一个House对象
        House house = new House();
        // 设置id
        house.setId(houseId);
        // 设置状态
        house.setStatus(status);
        // 调用HouseDao中更新的方法
        houseDao.update(house);
    }

    // 重写该方法是展示房源详细中户型楼层等信息
    @Override
    public House getById(Serializable id) {
        House house = houseDao.getById(id);
        // 获取户型
        String houseTypeName = dictDao.getNameById(house.getHouseTypeId());
        // 获取楼层
        String floorName = dictDao.getNameById(house.getFloorId());
        // 获取朝向
        String directionName= dictDao.getNameById(house.getDirectionId());
        // 获取建筑结构
        String buildStructureName = dictDao.getNameById(house.getBuildStructureId());
        // 获取装修情况
        String decorationName = dictDao.getNameById(house.getDecorationId());
        // 获取房屋用途
        String houseUserName = dictDao.getNameById(house.getHouseUseId());
        // 设置
        house.setHouseTypeName(houseTypeName);
        house.setFloorName(floorName);
        house.setDirectionName(directionName); //
        house.setBuildStructureName(buildStructureName);
        house.setDecorationName(decorationName);
        house.setHouseUseName(houseUserName);

        return house;

    }
}