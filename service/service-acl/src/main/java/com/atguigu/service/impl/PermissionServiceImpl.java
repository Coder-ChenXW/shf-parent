package com.atguigu.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Service;

import com.atguigu.PermissionService;
import com.atguigu.dao.BaseDao;
import com.atguigu.dao.PermissionDao;
import com.atguigu.dao.RolePermissionDao;
import com.atguigu.entity.Permission;
import com.atguigu.helper.PermissionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service(interfaceClass = PermissionService.class)
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements PermissionService {

    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private RolePermissionDao rolePermissionDao;

    @Override
    protected BaseDao<Permission> getEntityDao() {
        return permissionDao;
    }

    @Override
    public List<Map<String, Object>> findPermissionByRoleId(Long roleId) {
        // 获取所有的权限
        List<Permission> permissionList = permissionDao.findAll();
        // 根据角色id查询已分配的权限的id
        List<Long> permissionIds = rolePermissionDao.findPermissionIdsByRoleId(roleId);
        // 创建返回的List
        List<Map<String, Object>> returnList = new ArrayList<>();
        // 遍历所有的权限
        for (Permission permission : permissionList) {
            // 判断当前权限id在不在
            Map<String, Object> map = new HashMap();
            map.put("id", permission.getId());
            map.put("pId", permission.getParentId());
            map.put("name", permission.getName());

            if (permissionIds.contains(permission.getId())) {
                // 证明权限已经被分配
                map.put("checked", true);
            }
            returnList.add(map);
        }
        return returnList;
    }

    @Override
    public void saveRolePermissionRealtionShip(Long roleId, Long[] permissionIds) {

    }

    @Override
    public void assignPermission(Long roleId, Long[] permissionIds) {
        // 根据角色id删除已分配权限
        rolePermissionDao.deletePermissionIdsByRoleId(roleId);
        // 遍历所有权限id
        for (Long permissionId : permissionIds) {
            if (permissionId != null) {
                rolePermissionDao.addRoleIdAndPermission(roleId, permissionId);
            }

        }
    }

    @Override
    public List<Permission> getMenuPermissionsByAdminId(Long userId) {

        List<Permission> permissionList = null;
        // 判断是否是系统管理员
        if (userId == 1) {
            // 是系统管理员
            permissionList = permissionDao.findAll();
        } else {
            // 根据id查询权限菜单
            permissionList = permissionDao.getMenuPermissionsByAdminId(userId);
        }
        // 将list转换为树形结构
        List<Permission> treeList = PermissionHelper.bulid(permissionList);

        return treeList;
    }

    @Override
    public List<Permission> findAllMenu() {
        //全部权限列表
        List<Permission> permissionList = permissionDao.findAll();
        if(CollectionUtils.isEmpty(permissionList)) return null;

        //构建树形数据,总共三级
        //把权限数据构建成树形结构数据
        List<Permission> result = PermissionHelper.bulid(permissionList);
        return result;
    }

}