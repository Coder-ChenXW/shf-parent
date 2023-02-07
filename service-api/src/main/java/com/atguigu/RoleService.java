package com.atguigu;

import com.atguigu.entity.Role;
import com.atguigu.service.BaseService;


import java.util.List;
import java.util.Map;

public interface RoleService extends BaseService<Role> {

    List<Role> findAll();

    // 根据用户id查询用户的角色
    Map<String, Object> findRolesByAdminId(Long adminId);

    void assignRole(Long adminId, Long[] roleIds);
}
