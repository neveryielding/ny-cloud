package com.nycloud.admin.dto;

import lombok.Data;

/**
 * @description:
 * @author: super.wu
 * @date: Created in 2018/5/15 0015
 * @modified By:
 * @version: 1.0
 **/
@Data
public class PermissionMenuDto {

    private Integer permissionId;
    private Integer [] menuIds;

}
