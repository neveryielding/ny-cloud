package com.nycloud.admin.mapper;

import com.nycloud.admin.model.MenuTree;
import com.nycloud.admin.model.SysMenu;
import tk.mybatis.mapper.common.Mapper;
import java.util.List;

public interface SysMenuMapper extends Mapper<SysMenu> {

    /**
     * 查询所有可用的菜单
     * @param state
     * @return
     */
    List<MenuTree> selectByAll(Integer state);

    /**
     * 根据权限加载已分配的菜单列表
     * @param permissionId
     * @return
     */
    List<MenuTree> selectPermissionMenus(Integer permissionId);

    /**
     * 加载未分配给当前权限的菜单列表
     * @param permissionId
     * @return
     */
    List<MenuTree> selectPermissionNoExistMenus(Integer permissionId);

    /**
     * 查询用户关联角色关联权限关联的菜单
     * @param userId
     * @return
     */
    List<MenuTree> selectUserMenus(Long userId);
}