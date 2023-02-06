package com.atguigu;

import com.atguigu.entity.UserInfo;
import com.atguigu.service.BaseService;

public interface UserInfoService extends BaseService<UserInfo> {

    UserInfo getByPhone(String phone);
}