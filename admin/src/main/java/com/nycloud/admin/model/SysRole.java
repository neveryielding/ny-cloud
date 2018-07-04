package com.nycloud.admin.model;

import lombok.Data;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@Data
public class SysRole {

    @Id
    private Integer id;

    @NotEmpty(message = "角色名称不能为空")
    private String name;

    @NotEmpty(message = "角色编码不能为空")
    private String code;

    private String description;

}