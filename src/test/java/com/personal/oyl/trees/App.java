package com.personal.oyl.trees;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.personal.oyl.trees.structure.Tree;
import com.personal.oyl.trees.structure.TreeElement;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class App {
    /*public static void main(String[] args) {
        List<Data> dataset = new LinkedList<>();

        dataset.add(new Data(new String[]{"1", "1"}, "yes"));
        dataset.add(new Data(new String[]{"1", "1"}, "yes"));
        dataset.add(new Data(new String[]{"1", "0"}, "no"));
        dataset.add(new Data(new String[]{"0", "1"}, "no"));
        dataset.add(new Data(new String[]{"0", "1"}, "no"));
        
        List<String> labels = Arrays.asList("no surfacing","flippers");


        Gson gson = new GsonBuilder().setLenient().setPrettyPrinting().create();
        System.out.println(gson.toJson(Tree.INSTANCE.createTree(dataset, labels)));
    }*/

    public static void main(String[] args) {
        List<Data> dataset = new LinkedList<>();

        dataset.add(new Data(new String[]{"1", "1"}, "yes"));
        dataset.add(new Data(new String[]{"1", "1"}, "yes"));
        dataset.add(new Data(new String[]{"1", "0"}, "no"));
        dataset.add(new Data(new String[]{"0", "1"}, "no"));
        dataset.add(new Data(new String[]{"0", "1"}, "no"));

        System.out.println(TreeUtil.INSTANCE.calcShannonEnt(dataset));

        dataset.add(new Data(new String[]{"0", "-1"}, "maybe"));
        System.out.println(TreeUtil.INSTANCE.calcShannonEnt(dataset));

//        List<String> labels = Arrays.asList("no surfacing","flippers");
//
//        TreeElement tree = TreeUtil.INSTANCE.createTree(dataset, labels);
//        Gson gson = new GsonBuilder().setLenient().setPrettyPrinting().create();
//        System.out.println(gson.toJson(tree));
//
//        System.out.println(TreeUtil.INSTANCE.classfy((Tree) tree, Arrays.asList("no surfacing","flippers"), Arrays.asList("1","1")));
//        System.out.println(TreeUtil.INSTANCE.classfy((Tree) tree, Arrays.asList("no surfacing","flippers"), Arrays.asList("1","0")));
//        System.out.println(TreeUtil.INSTANCE.classfy((Tree) tree, Arrays.asList("no surfacing","flippers"), Arrays.asList("0","1")));
//        System.out.println(TreeUtil.INSTANCE.classfy((Tree) tree, Arrays.asList("no surfacing","flippers"), Arrays.asList("0","0")));

    }
}
