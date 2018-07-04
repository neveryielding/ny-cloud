package com.nycloud.admin.model;

import lombok.Data;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Data
public class SysUserGroup {

    @Id
    private Integer id;

    @NotBlank
    private String name;

    private String code;

    private String description;


}