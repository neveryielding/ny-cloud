package com.nycloud.admin.service;

import com.nycloud.admin.mapper.SysPermissionMapper;
import com.nycloud.admin.mapper.SysPermissionMenuPkMapper;
import com.nycloud.admin.mapper.SysPermissionResourcePkMapper;
import com.nycloud.admin.mapper.SysResourceMapper;
import com.nycloud.admin.model.SysPermission;
import com.nycloud.admin.model.SysPermissionMenuPk;
import com.nycloud.admin.model.SysPermissionResourcePk;
import com.nycloud.admin.model.SysResource;
import com.nycloud.common.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @description:
 * @author: super.wu
 * @date: Created in 2018/5/15 0015
 * @modified By:
 * @version: 1.0
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class SysPermissionService extends BaseService<SysPermissionMapper, SysPermission> {

    @Autowired
    private SysPermissionMenuPkMapper sysPermissionMenuPkMapper;

    @Autowired
    private SysPermissionResourcePkMapper sysPermissionResourcePkMapper;

    @Autowired
    private SysResourceMapper sysResourceMapper;

    public void delete(int id) {
        // 删除关联菜单
        SysPermissionMenuPk permissionMenuPk = new SysPermissionMenuPk();
        permissionMenuPk.setPermissionId(id);
        sysPermissionMenuPkMapper.delete(permissionMenuPk);
        // 删除关联资源
        SysPermissionResourcePk permissionResourcePk = new SysPermissionResourcePk();
        permissionResourcePk.setPermissionId(id);
        sysPermissionResourcePkMapper.delete(permissionResourcePk);
        // 删除权限
        this.deleteById(id);
    }

    public List<SysResource> selectPermissionResource(int permissionId) {
        List<SysResource> allSysResources = this.sysResourceMapper.selectAllResources();
        List<SysResource> currentSysResources = this.sysResourceMapper.selectPermissionResources(permissionId);
        List<Long> resourcesIds = new ArrayList<>();
        if (!ListUtils.isEmpty(currentSysResources)) {
            currentSysResources.forEach(item -> {
                resourcesIds.add(item.getId());
            });
        }
        List<SysResource> sysResources = new ArrayList<>();
        SysResource parent = null;
        for (int i = 0; i < allSysResources.size(); i ++) {
            SysResource item = allSysResources.get(i);
            if (resourcesIds.size() > 0 && resourcesIds.contains(item.getId())) {
                item.setChecked(true);
            }
            if (item.getLevel() == 1) {
                sysResources.add(item);
            } else {
                if (parent == null || !item.getParentId().equals(parent.getId())) {
                    for (int j = 0; j < sysResources.size(); j ++) {
                        SysResource parentItem = sysResources.get(j);
                        if (item.getParentId().equals(parentItem.getId())) {
                            parent = parentItem;
                            parent.setChildren(new ArrayList<>());
                            break;
                        }
                    }
                }
                parent.getChildren().add(item);
            }
        }
        return sysResources;
    }

    public Integer batchInsertPermissionResource(List<SysPermissionResourcePk> list) {
        SysPermissionResourcePk pk = new SysPermissionResourcePk();
        pk.setPermissionId(list.get(0).getPermissionId());
        this.sysPermissionResourcePkMapper.delete(pk);
        return this.sysPermissionResourcePkMapper.insertPermissionResources(list);
    }

}
