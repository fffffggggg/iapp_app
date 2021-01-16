package com.convenient.iapp.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString
public class CityTree extends TreeNode{

    private String name;


}
