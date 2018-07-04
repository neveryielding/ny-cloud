package com.nycloud.admin.dto;

import com.nycloud.common.dto.RequestDto;
import lombok.Data;

/**
 * @description:
 * @author: super.wu
 * @date: Created in 2018/5/14 0014
 * @modified By:
 * @version: 1.0
 **/
@Data
public class RolePermissionDto extends RequestDto {

    /**
     * 角色Id
     */
    private Integer roleId;

    /**
     * 多个权限Id
     */
    private Integer [] permissionIds;

}
