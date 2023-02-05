package com.atguigu;

import com.atguigu.entity.Role;
import com.atguigu.service.BaseService;


import java.util.List;

public interface RoleService extends BaseService<Role> {

    List<Role> findAll();

}
