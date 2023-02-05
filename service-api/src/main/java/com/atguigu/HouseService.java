package com.atguigu;

import com.atguigu.entity.House;
import com.atguigu.service.BaseService;

public interface HouseService extends BaseService<House> {
    // 发布或取消
    void publish(Long houseId, Integer status);
}
