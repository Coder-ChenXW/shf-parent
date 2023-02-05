package com.atguigu;

import com.atguigu.entity.HouseBroker;
import com.atguigu.service.BaseService;

import java.util.List;

public interface HouseBrokerService extends BaseService<HouseBroker> {

    List<HouseBroker> getHouseBrokersByHouseId(Long houseId);

}
