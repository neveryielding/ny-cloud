package com.nycloud.admin.util;

import com.nycloud.admin.model.MenuTree;
import com.nycloud.admin.util.comparator.MenuTreeComparator;
import com.nycloud.common.utils.ListUtils;
import java.util.*;

/**
 * @description: 菜单树工具类，菜单树生成
 * @author: super.wu
 * @date: Created in 2018/5/17 0017
 * @modified By:
 * @version: 1.0
 **/
public class MenuTreeUtil {

    /**
     * 在所有菜单中过滤当前菜单并排序好菜单 然后生成菜单树
     * @param allList   所有菜单集合
     * @param currentList 当前菜单集合
     * @return
     */
    public static List<MenuTree> filterGenerateSortMenu(List<MenuTree> allList, List<MenuTree> currentList) {
        if (ListUtils.isEmpty(allList) || ListUtils.isEmpty(currentList)) {
            return null;
        }
        // 所有菜单
        Map<Integer, MenuTree> allMap = new HashMap<>(allList.size());
        // 当前菜单
        Map<Integer, MenuTree> currentMap = new HashMap<>(1);
        allList.stream().forEach(menuTree -> {
            allMap.put(menuTree.getId(), menuTree);
        });
        currentList.stream().forEach(currentNode -> {
            // 判断是顶级节点 直接添加即可
            if (currentNode.getLevel() == 1) {
                currentNode.setFindParentNode(true);
                currentMap.put(currentNode.getId(), currentNode);
            } else {
                // 是否终止While循环，默认否
                boolean isBreakWhile = false;
                // 需要处理的节点
                MenuTree nextNode = currentNode;
                // 启动循环直至于找到自己的顶级父节点为止
                while (!isBreakWhile) {
                    // 获取当前父节点并判断当前节点的父节点是否已经被添加
                    MenuTree parentNode = currentMap.get(nextNode.getParentId());
                    if (parentNode == null) {
                        // 从所有节点中取出当前父节点
                        parentNode = allMap.get(nextNode.getParentId());
                    }
                    // 当前节点的父节点已经找到 并添加到当前节点集合
                    nextNode.setFindParentNode(true);
                    currentMap.put(nextNode.getId(), nextNode);
                    // 当前父节点为顶级节点或者当前节点的父节点已经找到，则直接添加父节点，并终止循环
                    if (parentNode.getLevel() == 1 || parentNode.isFindParentNode()) {
                        currentMap.put(parentNode.getId(), parentNode);
                        isBreakWhile = true;
                    } else {
                        // 继续寻找上层的父节点
                        nextNode = parentNode;
                    }
                }
            }
        });
        // 创建一个排序的集合
        List<MenuTree> sortList = new ArrayList<>();
        // 迭代出所有的当前节点并添加进排序集合
        Iterator<Map.Entry<Integer, MenuTree>> iterator = currentMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, MenuTree> entry = iterator.next();
            sortList.add(currentMap.get(entry.getKey()));
        }
        // 按照规则进行数据自定义排序处理
        Collections.sort(sortList, new MenuTreeComparator());
        return generateMenuTree(sortList);
    }


    /**
     * 生成菜单树
     * @param sortList 已经按照规则排好序的菜单集合
     * @return
     */
    public static List<MenuTree> generateMenuTree(List<MenuTree> sortList) {
        if (ListUtils.isEmpty(sortList)) {
            return sortList;
        }
        List<MenuTree> nodeList = new ArrayList<>();
        sortList.stream().forEach(currentNode -> {
            // 确先将所有的一级节点添加进集合
            if (currentNode.getLevel() == 1) {
                currentNode.setChildren(new ArrayList<>());
                nodeList.add(currentNode);
            } else {
                for (int i = 0; i < nodeList.size(); i ++) {
                    MenuTree parentNode = nodeList.get(i);
                    // 判断如果不是当前父节点，不是则无限遍历寻找父节点
                    if (!currentNode.getParentId().equals(parentNode.getId())) {
                        infiniteTraversalMenu(nodeList, currentNode);
                    } else {
                        // 设置当前节点的父节点名称，并终止当前循环
                        currentNode.setParentName(currentNode.getTitle());
                        parentNode.getChildren().add(currentNode);
                        break;
                    }
                }
            }
        });
        return nodeList;
    }


    /**
     * 无限遍历菜单添加子节点
     * @param nodeList
     * @param currentNode
     */
    private static void infiniteTraversalMenu(List<MenuTree> nodeList, MenuTree currentNode) {
        for (int i = 0; i < nodeList.size(); i ++) {
            MenuTree node = nodeList.get(i);
            // 判断不是当前节点的父节点
            if (!node.getId().equals(currentNode.getParentId())) {
                // 如果节点有子节点则递归调用当前方法继续匹配, 匹配完后终止当前循环
                if (node.getChildren() != null && node.getChildren().size() > 0) {
                    infiniteTraversalMenu(node.getChildren(), currentNode);
                    break;
                } else {
                    continue;
                }
            } else {
                // 设置当前节点的父节点信息
                currentNode.setParentName(node.getTitle());
                if (node.getChildren() == null) {
                    node.setChildren(new ArrayList<>());
                }
                node.getChildren().add(currentNode);
                break;
            }
        }
    }


}
