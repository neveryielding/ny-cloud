package com.nycloud.admin.model;

import lombok.Data;
import javax.persistence.Id;

@Data
public class SysPermission {

    @Id
    private Integer id;

    private String name;

    private String description;

    private int state;

}