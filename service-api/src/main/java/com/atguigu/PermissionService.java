package com.atguigu;

import com.atguigu.entity.Permission;
import com.atguigu.service.BaseService;

import java.util.List;
import java.util.Map;

public interface PermissionService extends BaseService<Permission> {


    /**
     * 根据角色获取授权权限数据
     * @return
     */
    List<Map<String,Object>> findPermissionByRoleId(Long roleId);

    /**
     * 保存角色权限
     * @param roleId
     * @param permissionIds
     */
    void saveRolePermissionRealtionShip(Long roleId, Long[] permissionIds);

    void assignPermission(Long roleId, Long[] permissionIds);

    List<Permission> getMenuPermissionsByAdminId(Long userId);

    /**
     * 菜单全部数据
     * @return
     */
    List<Permission> findAllMenu();

    List<String> getPermissionCodeByAdminId(Long id);
}