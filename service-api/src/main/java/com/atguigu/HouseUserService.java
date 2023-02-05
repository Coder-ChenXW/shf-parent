package com.atguigu;

import com.atguigu.entity.HouseUser;
import com.atguigu.service.BaseService;

import java.util.List;

public interface HouseUserService extends BaseService<HouseUser> {

    List<HouseUser> getHouseUsersByHouseId(Long houseId);

}
