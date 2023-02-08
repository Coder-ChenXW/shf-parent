package com.atguigu;

import com.atguigu.entity.Admin;
import com.atguigu.service.BaseService;

import java.util.List;


public interface AdminService extends BaseService<Admin> {
    List<Admin> findAll();

    Admin getAdminByUsername(String username);
}
