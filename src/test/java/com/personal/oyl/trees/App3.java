package com.personal.oyl.trees;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.personal.oyl.trees.structure.Tree;
import com.personal.oyl.trees.structure.TreeElement;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class App3 {
    public static void main(String[] args) {
        List<Data> dataset = new LinkedList<>();

        dataset.add(new Data(new String[]{"youth", "high", "no", "fair"}, "no"));
        dataset.add(new Data(new String[]{"youth", "high", "no", "excellent"}, "no"));
        dataset.add(new Data(new String[]{"middle", "high", "no", "fair"}, "yes"));
        dataset.add(new Data(new String[]{"senior", "medium", "no", "fair"}, "yes"));
        dataset.add(new Data(new String[]{"senior", "low", "yes", "fair"}, "yes"));

        dataset.add(new Data(new String[]{"senior", "low", "yes", "excellent"}, "no"));
        dataset.add(new Data(new String[]{"middle", "low", "yes", "excellent"}, "yes"));
        dataset.add(new Data(new String[]{"youth", "medium", "no", "fair"}, "no"));
        dataset.add(new Data(new String[]{"youth", "low", "yes", "fair"}, "yes"));
        dataset.add(new Data(new String[]{"senior", "medium", "yes", "fair"}, "yes"));

        dataset.add(new Data(new String[]{"youth", "medium", "yes", "excellent"}, "yes"));
        dataset.add(new Data(new String[]{"middle", "medium", "no", "excellent"}, "yes"));
        dataset.add(new Data(new String[]{"middle", "high", "yes", "fair"}, "yes"));
        dataset.add(new Data(new String[]{"senior", "medium", "no", "excellent"}, "no"));

        TreeElement tree = TreeUtil.INSTANCE.createTree(dataset, Arrays.asList("age", "income", "student", "credit_rating"));
        Gson gson = new GsonBuilder().setLenient().setPrettyPrinting().create();
        System.out.println(gson.toJson(tree));
    }

}
