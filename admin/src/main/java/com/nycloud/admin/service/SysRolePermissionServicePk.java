package com.nycloud.admin.service;

import com.github.pagehelper.PageHelper;
import com.nycloud.admin.dto.RolePermissionDto;
import com.nycloud.admin.mapper.*;
import com.nycloud.admin.model.SysRolePermissionPk;
import com.nycloud.common.vo.ResponsePage;
import org.apache.commons.lang3.StringUtils;
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
public class SysRolePermissionServicePk extends BaseService<SysRolePermissionPkMapper, SysRolePermissionPk> {

    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    public void setSysPermissionMapper(SysPermissionMapper sysPermissionMapper) {
        this.sysPermissionMapper = sysPermissionMapper;
    }

    public ResponsePage loadRoleNoRelationPermissions(RolePermissionDto dto){
        PageHelper.startPage(dto.getPage(), dto.getSize());
        Map<String, Object> map = getQueryParams(dto);
        return new ResponsePage<>(sysPermissionMapper.selectRoleNoPermissions(map));
    }

    public ResponsePage loadRolePermissions(RolePermissionDto dto){
        PageHelper.startPage(dto.getPage(), dto.getSize());
        Map<String, Object> map = getQueryParams(dto);
        return new ResponsePage<>(sysPermissionMapper.selectRolePermissions(map));
    }

    private Map<String, Object> getQueryParams(RolePermissionDto dto) {
        return new HashMap<java.lang.String, java.lang.Object>(1){{
            put("roleId", dto.getRoleId());
            if (StringUtils.isNotBlank(dto.getName())) {
                put("name", dto.getName());
            }
        }};
    }

    public Integer batchInsert(List<SysRolePermissionPk> list) {
        return this.mapper.insertRolePermissions(list);
    }

    public Integer batchDelete(Integer roleId, Integer [] permissionIds) {
        Map<String, Object> map = new HashMap<String, Object>(2){{
            put("roleId", roleId);
            put("permissionIds", permissionIds);
        }};
        return this.mapper.deleteRolePermissions(map);
    }
}
