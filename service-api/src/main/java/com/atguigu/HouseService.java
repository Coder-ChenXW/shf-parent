package com.atguigu;

import com.atguigu.entity.House;
import com.atguigu.service.BaseService;
import com.atguigu.vo.HouseQueryVo;
import com.atguigu.vo.HouseVo;
import com.github.pagehelper.PageInfo;

public interface HouseService extends BaseService<House> {
    // 发布或取消
    void publish(Long houseId, Integer status);

    // 端分页及带条件查询的方法
    PageInfo<HouseVo> findListPage(int pageNum, int pageSize, HouseQueryVo houseQueryVo);
}
