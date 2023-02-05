package com.atguigu;

import com.atguigu.entity.HouseImage;
import com.atguigu.service.BaseService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HouseImageService extends BaseService<HouseImage> {

    List<HouseImage> getHouseImagesByHouseIdAndType(Long houseId, Integer type);


}
