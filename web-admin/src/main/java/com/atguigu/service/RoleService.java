package com.atguigu.service;

import com.atguigu.entity.Role;

import java.util.List;

public interface RoleService {

    List<Role> findAll();


    Integer insert(Role role);

    void delete(Long roleId);

    Role getById(Long roleId);

    Integer update(Role role);
}
