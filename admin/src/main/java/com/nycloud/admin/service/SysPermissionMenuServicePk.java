package com.nycloud.admin.service;

import com.nycloud.admin.mapper.SysMenuMapper;
import com.nycloud.admin.mapper.SysPermissionMenuPkMapper;
import com.nycloud.admin.model.MenuTree;
import com.nycloud.admin.model.SysPermissionMenuPk;
import com.nycloud.admin.util.MenuTreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: super.wu
 * @date: Created in 2018/5/15 0015
 * @modified By:
 * @version: 1.0
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class SysPermissionMenuServicePk extends BaseService<SysPermissionMenuPkMapper, SysPermissionMenuPk> {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    public List<MenuTree> loadPermissionNoMenuTree(Integer permissionId){
        List<MenuTree> allList = sysMenuMapper.selectByAll(1);
        List<MenuTree> currentList = sysMenuMapper.selectPermissionNoExistMenus(permissionId);
        return MenuTreeUtil.filterGenerateSortMenu(allList, currentList);
    }

    public List<MenuTree> loadPermissionMenuTree(Integer permissionId){
        List<MenuTree> allList = sysMenuMapper.selectByAll(1);
        List<MenuTree> currentList = sysMenuMapper.selectPermissionMenus(permissionId);
        return MenuTreeUtil.filterGenerateSortMenu(allList, currentList);
    }

    public Integer batchInsert(List<SysPermissionMenuPk> list) {
        return this.mapper.insertPermissionMenus(list);
    }

    public Integer batchDelete(Integer permissionId, Integer [] menuIds) {
        Map<String, Object> map = new HashMap<String, Object>(2){{
            put("permissionId", permissionId);
            put("menuIds", menuIds);
        }};
        return this.mapper.deletePermissionMenus(map);
    }
}
