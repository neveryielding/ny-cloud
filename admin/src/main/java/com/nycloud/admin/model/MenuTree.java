package com.nycloud.admin.model;

import lombok.Data;
import java.util.List;

/**
 * @description: 扩展类菜单树
 * @author: super.wu
 * @date: Created in 2018/5/11 0011
 * @modified By:
 * @version: 1.0
 **/
@Data
public class MenuTree extends SysMenu {

    /**
     * 扩展属性 父级名称
     **/
    private String parentName;
    /**
     * 扩展属性 子级列表
     **/
    private List<MenuTree> children;
    /**
     *  是否找到当前父节点，MenuTreeUtil类遍历节点的时候用，保证所有父节点只会被查找一次，不会重复查询
     */
    private boolean findParentNode;

}