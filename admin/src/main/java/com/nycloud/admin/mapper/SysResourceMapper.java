package com.nycloud.admin.mapper;

import com.nycloud.admin.model.SysResource;
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
public interface SysResourceMapper extends Mapper<SysResource>{

    /**
     * 查询某个权限关联的资源
     * @param permissionId
     * @return
     */
    List<SysResource> selectPermissionResources(Integer permissionId);

    /**
     * 查询某个用户的资源
     * @param userId
     * @return
     */
    List<SysResource> selectUserResources(Long userId);

    /**
     * 查询所有资源
     * @return
     */
    List<SysResource> selectAllResources();

    /**
     * 查询用户访问的某个资源是否被关联
     * @param map
     * @return
     */
    SysResource selectUserRolePermissionResource(Map<String, Object> map);

    /**
     * 查询用户所有的资源编码
     * @param userId
     * @return
     */
    List<String> selectUserAllResourceCodes(Long userId);

    /**
     * 查询用户下的一个或多个资源编码
     * @param map
     * @return
     */
    List<String> selectUserResourceCodes(Map<String, Object> map);

}
