package com.nycloud.admin.exception;

import com.nycloud.admin.model.MenuTree;

/**
 * @description:
 * @author: super.wu
 * @date: Created in 2018/5/17 0017
 * @modified By:
 * @version: 1.0
 **/
public class NotFoundParentNodeException extends Exception {

    public NotFoundParentNodeException() {}

    public NotFoundParentNodeException(MenuTree node) {
        super(new StringBuffer("菜单错误错误 无法找到父节点 ").append(node.toString()).toString());
    }

}
