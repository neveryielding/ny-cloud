package com.nycloud.admin.model;

import lombok.Data;
import javax.persistence.Id;

@Data
public class SysMenu {

    /** 菜单Id **/
    @Id
    protected Integer id;
    /** 菜单标题 **/
    protected String title;
    /** 菜单图标 **/
    protected String icon;
    /** 父级Id **/
    protected Integer parentId;
    /** 排序 **/
    protected Integer sort;
    /** 等级 **/
    protected Integer level;
    /** 前端国际化名称 **/
    protected String name;
    /** 前端访问路径 **/
    protected String path;
    /** 前端组件 **/
    protected String component;
    /** 描述 **/
    protected String description;
    /** 是否启用 **/
    protected Integer state;


}