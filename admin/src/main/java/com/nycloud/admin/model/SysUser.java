package com.nycloud.admin.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.nycloud.common.utils.ToStringDateSerializer;
import lombok.Data;
import javax.persistence.Id;
import java.util.List;

@Data
public class SysUser {

    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String username;

    private String password;

    @JsonSerialize(using = ToStringDateSerializer.class)
    private Long createTime;

    @JsonSerialize(using = ToStringDateSerializer.class)
    private Long lastPasswordChange;

    private Integer state;

    private String authorities;

    private String name;


    /** 增加属性 不含当前实体在数据库字段映射 **/

    /**
     * 用户资源
     */
    private List<SysResource> resourceList;


}