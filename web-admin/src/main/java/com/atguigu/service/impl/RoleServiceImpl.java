package com.atguigu.service.impl;


import com.atguigu.dao.RoleDao;
import com.atguigu.entity.Role;
import com.atguigu.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
//    @Resource
    private RoleDao roleDao;

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }


    @Override
    public Integer insert(Role role) {
        return roleDao.insert(role);
    }

    @Override
    public void delete(Long roleId) {
        roleDao.delete(roleId);
    }

    @Override
    public Role getById(Long roleId) {

        return roleDao.getById(roleId);
    }

    @Override
    public Integer update(Role role) {
        return roleDao.update(role);
    }
}
