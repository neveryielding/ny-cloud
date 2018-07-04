package com.nycloud.admin.mapper;

import com.nycloud.admin.model.SysPermissionResourcePk;
import tk.mybatis.mapper.common.Mapper;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: super.wu
 * @date: Created in 2018/5/21 0021
 * @modified By:
 * @version: 1.0
 **/
public interface SysPermissionResourcePkMapper extends Mapper<SysPermissionResourcePk> {

    Integer insertPermissionResources(List<SysPermissionResourcePk> list);

    Integer deletePermissionResources(Map<String, Object> map);
}
