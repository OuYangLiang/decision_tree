package com.personal.oyl.trees.structure;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
public class Tree implements TreeElement {
    private String label;
    private Map<String, TreeElement> dict;

    public Tree(String label) {
        this.label = label;
        this.dict = new HashMap<>();
    }
}
