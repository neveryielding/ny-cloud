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
public class UserGroupRoleDto extends RequestDto {

    /**
     * 用户Id
     */
    private int groupId;

    /**
     * 多个角色Id
     */
    private int [] roleIds;

}
