package com.convenient.iapp.util;

import com.convenient.iapp.entity.TreeNode;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class TreeUtil {

    /**
     * 两层循环实现建树
     * @param treeNodes
     * @param root
     * @param <T>
     * @return
     */
    public <T extends TreeNode> List<T> build(List<T> treeNodes, Object root){
        List<T> trees = new ArrayList<>();
        for(T treeNode : treeNodes){
            if(root.equals(treeNode.getPid())){
                trees.add(treeNode);
            }

            for(T it : treeNodes){
                if(it.getPid() .equals(treeNode.getId())){
                    if(treeNode.getChildren() == null){
                        treeNode.setChildren(new ArrayList<>());
                    }
                    treeNode.add(it);
                }
            }
        }
        return trees;
    }

    /**
     * 使用递归方法建树
     * @param root
     * @param treeNodes
     * @param <T>
     * @return
     */
    public <T extends TreeNode> List<T> buildByRecursive(List<T> treeNodes, Object root){
        List<T> trees = new ArrayList<>();
        for(T treeNode : treeNodes){
            if (root.equals(treeNode.getPid())) {
                trees.add(findChildren(treeNode, treeNodes));
            }
        }
        return trees;
    }

    /**
     * 递归查找子节点
     * @param treeNode
     * @param treeNodes
     * @param <T>
     * @return
     */
    public <T extends TreeNode> T findChildren(T treeNode, List<T> treeNodes){
        for(T it : treeNodes){
            if(treeNode.getId().equals( it.getPid())){
                if(treeNode.getChildren() == null){
                    treeNode.setChildren(new ArrayList<>());
                }
                treeNode.add(findChildren(it, treeNodes));
            }
        }
        return treeNode;
    }
}
