package com.nycloud.admin.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import javax.persistence.Id;
import java.util.List;

@Data
public class SysResource {

    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String name;

    private String code;

    private String pageElements;

    private String url;

    private String urlRequestType;

    private String description;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long parentId;

    private String parentName;

    private Integer level;

    private Integer state;

    /** 扩展属性 资源组下的资源信息 **/
    private List<SysResource> children;
    /** 扩展属性 资源是否被选中 **/
    private boolean checked;

}