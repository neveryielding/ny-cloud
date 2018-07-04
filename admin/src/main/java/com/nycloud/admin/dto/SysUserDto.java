package com.nycloud.admin.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;

/**
 * @description:
 * @author: super.wu
 * @date: Created in 2018/6/12 0012
 * @modified By:
 * @version: 1.0
 **/
@Data
public class SysUserDto {

    @NotBlank
    private String username;

    private String password;

    @NotBlank
    private String name;

    @NotBlank
    private String groupId;

    @NotBlank
    private String state;

    private String id;
}
