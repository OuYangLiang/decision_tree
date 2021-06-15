package com.personal.oyl.trees;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.personal.oyl.trees.structure.Tree;
import com.personal.oyl.trees.structure.TreeElement;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class App2 {
    public static void main(String[] args) throws IOException {
        List<String> lines = FileReader.INSTANCE.read("./lenses.txt");
        List<Data> dataset = new LinkedList<>();
        for (String line : lines) {
            String[] parts = line.trim().split("\\t");
            dataset.add(new Data(new String[]{parts[0], parts[1], parts[2], parts[3]}, parts[4]));
        }

        List<String> labels = Arrays.asList("age","prescript", "astigmatic", "tearRate");

        TreeElement tree = TreeUtil.INSTANCE.createTree(dataset, labels);
        Gson gson = new GsonBuilder().setLenient().setPrettyPrinting().create();
        System.out.println(gson.toJson(tree));

        System.out.println(TreeUtil.INSTANCE.classfy((Tree) tree, Arrays.asList("age","prescript", "astigmatic", "tearRate"),
                Arrays.asList("young", "myope", "no", "normal")));
    }
}
