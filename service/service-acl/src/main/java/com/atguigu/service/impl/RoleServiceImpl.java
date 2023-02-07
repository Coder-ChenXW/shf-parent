package com.atguigu.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.RoleService;
import com.atguigu.dao.AdminDao;
import com.atguigu.dao.AdminRoleDao;
import com.atguigu.dao.BaseDao;
import com.atguigu.dao.RoleDao;
import com.atguigu.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service(interfaceClass = RoleService.class)
@Transactional
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {

    @Autowired
//    @Resource
    private RoleDao roleDao;

    @Autowired
    private AdminRoleDao adminRoleDao;

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }


    @Override
    public Map<String, Object> findRolesByAdminId(Long adminId) {
        // 获取所有的角色
        List<Role> roleList = roleDao.findAll();
        // 获取用户已有的角色
        List<Long> roleIds = adminRoleDao.findRoleIdsByAdminId(adminId);
        // 创建两个list
        List<Role> noAssginRoleList = new ArrayList<>();
        List<Role> assginRoleList = new ArrayList<>();
        // 遍历所有角色
        for (Role role : roleList) {
            // 判断当前角色的id在不在集合roleIds中
            if (roleIds.contains(role.getId())) {
                // 将当前角色放到已选中的List中
                assginRoleList.add(role);
            } else {
                // 当前角色是未选中角色
                noAssginRoleList.add(role);
            }
        }

        // 创建返回map
        Map<String, Object> roleMap = new HashMap<>();
        roleMap.put("noAssginRoleList", noAssginRoleList);
        roleMap.put("assginRoleList", assginRoleList);
        return roleMap;
    }

    @Override
    public void assignRole(Long adminId, Long[] roleIds) {
        // 根据用户id将已分配的角色删除
        adminRoleDao.deleteRoleIdsByAdminId(adminId);
        // 遍历所有的角色id
        for (Long roleId : roleIds) {
            if (roleId != null) {
                adminRoleDao.addRoleIdAndAdminId(roleId, adminId);
            }

        }
    }

    @Override
    protected BaseDao<Role> getEntityDao() {
        return this.roleDao;
    }
}
