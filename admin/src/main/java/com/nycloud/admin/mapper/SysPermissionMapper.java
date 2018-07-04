package com.nycloud.admin.mapper;

import com.nycloud.admin.model.SysPermission;
import com.nycloud.admin.model.SysRole;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: super.wu
 * @date: Created in 2018/5/15 0015
 * @modified By:
 * @version: 1.0
 **/
public interface SysPermissionMapper extends Mapper<SysPermission>{

    List<SysPermission> selectRoleNoPermissions(Map<String, Object> map);

    List<SysPermission> selectRolePermissions(Map<String, Object> map);

}
