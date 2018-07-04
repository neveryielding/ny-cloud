package com.nycloud.admin.dto;

import com.nycloud.common.dto.RequestDto;
import lombok.Data;

/**
 * @description:
 * @author: super.wu
 * @date: Created in 2018/5/21 0021
 * @modified By:
 * @version: 1.0
 **/
@Data
public class PermissionResourceDto extends RequestDto{

    private Integer permissionId;

    private Long [] resourceIds;

}
