package com.nycloud.admin.mapper;

import com.nycloud.admin.model.SysUserGroupRolePk;
import tk.mybatis.mapper.common.Mapper;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: super.wu
 * @date: Created in 2018/5/14 0014
 * @modified By:
 * @version: 1.0
 **/
public interface SysUserGroupRolePkMapper extends Mapper<SysUserGroupRolePk> {

    Integer insertGroupRoles(List<SysUserGroupRolePk> list);

    Integer deleteGroupRoles(Map<String, Object> map);

}
