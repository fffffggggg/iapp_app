package com.convenient.iapp.entity;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TreeNode {

    private String id;
    private String pid;
    private String name;
    private String leaf;

    private List<TreeNode> children = new ArrayList<>();

    public void add(TreeNode node){
        children.add(node);
    }

}
