package com.nycloud.auth.model;

import lombok.Data;

@Data
public class Resource {

    private Long id;

    private String name;

    private String code;

    private String pageElements;

    private String url;

    private String urlRequestType;

    private String description;

    private Long parentId;

    private String parentName;

    private Integer level;

    private Integer state;

    private Integer type;


}