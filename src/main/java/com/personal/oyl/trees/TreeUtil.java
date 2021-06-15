package com.personal.oyl.trees;

import com.personal.oyl.trees.structure.Klass;
import com.personal.oyl.trees.structure.Tree;
import com.personal.oyl.trees.structure.TreeElement;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum TreeUtil {
    INSTANCE;

    public BigDecimal calcShannonEnt(List<Data> dataset) {
        BigDecimal num = BigDecimal.valueOf(dataset.size());
        Map<String, BigDecimal> labelCounts = dataset.stream().map(Data::getKlass)
                .collect(Collectors.toMap(Function.identity(), t -> BigDecimal.ONE, BigDecimal::add));

        BigDecimal rlt = BigDecimal.ZERO;
        for (Map.Entry<String, BigDecimal> item : labelCounts.entrySet()) {
            BigDecimal prob = item.getValue().divide(num, MathContext.DECIMAL128);
            rlt = rlt.add((prob.multiply(this.log2(prob), MathContext.DECIMAL128)));
        }

        return rlt.multiply(BigDecimal.valueOf(-1));
    }

    public List<Data> split(List<Data> dataset, int index, String val) {
        List<Data> rlt = new LinkedList<>();
        for (Data item : dataset) {
            if (item.getProperties()[index].equals(val)) {
                int idx = 0;
                String[] props = new String[item.getProperties().length - 1];
                for (int i = 0; i < props.length; i++) {
                    if (idx == index) {
                        idx++;
                    }
                    props[i] = item.getProperties()[idx++];
                }
                rlt.add(new Data(props, item.getKlass()));
            }
        }

        return rlt;
    }

    public int chooseBestFeatureToSplit(List<Data> dataset) {
        int numFeatures = dataset.get(0).getProperties().length;
        BigDecimal baseEntropy = this.calcShannonEnt(dataset);
        BigDecimal bestInfoGain = BigDecimal.ZERO;
        int bestFeature = -1;

        for (int i = 0; i < numFeatures; i++) {
            final int j = i;
            // 取出去重后i列的值，存入uniqueVals
            Set<String> uniqueVals = dataset.stream().map(t -> t.getProperties()[j]).collect(Collectors.toSet());
            BigDecimal newEntropy = BigDecimal.ZERO;

            for (String item : uniqueVals) {
                List<Data> subDataset = this.split(dataset, i, item);
                BigDecimal prob = BigDecimal.valueOf(subDataset.size()).divide(BigDecimal.valueOf(dataset.size()), MathContext.DECIMAL128);
                newEntropy = newEntropy.add(prob.multiply(this.calcShannonEnt(subDataset), MathContext.DECIMAL128));
            }

            BigDecimal infoGained = baseEntropy.subtract(newEntropy);
            if (infoGained.compareTo(bestInfoGain) > 0) {
                bestInfoGain = infoGained;
                bestFeature = i;
            }
        }

        return bestFeature;
    }

    // 出现次数最多的类别
    public String majorityCnt(List<String> klasses) {
        Map<String, Integer> klassesCnt = klasses.stream().collect(Collectors.toMap(Function.identity(), t -> 1, Integer::sum));
        return klassesCnt.entrySet().stream()
                .sorted(Comparator.comparingInt(Map.Entry::getValue))
                .collect(Collectors.toList())
                .get(klassesCnt.size() - 1).getKey();
    }

    public TreeElement createTree(List<Data> dataset, List<String> lables) {
        List<String> lablesClone = new LinkedList<>(lables);
        List<String> klasses = dataset.stream().map(Data::getKlass).collect(Collectors.toList());
        if (new HashSet<>(klasses).size() == 1) {
            // 如果只有一个类别，直接返回该类别
            return new Klass(klasses.get(0));
        }

        if (0 == dataset.get(0).getProperties().length) {
            return new Klass(this.majorityCnt(klasses));
        }

        final int bestFeat = this.chooseBestFeatureToSplit(dataset);
        String bestFeatLabel = lablesClone.get(bestFeat);

        Tree tree = new Tree(bestFeatLabel);
        lablesClone.remove(bestFeat);
        Set<String> uniqueVals = dataset.stream().map(t -> t.getProperties()[bestFeat]).collect(Collectors.toSet());
        for (String val : uniqueVals) {
            tree.getDict().put(val, this.createTree(this.split(dataset, bestFeat, val), new LinkedList<>(lablesClone)));
        }

        return tree;
    }

    public String classfy(Tree tree, List<String> featLabels, List<String> testVec) {
        String firstStr = tree.getLabel();
        Map<String, TreeElement> secondDict = tree.getDict();

        int featIndex = featLabels.indexOf(firstStr);

        for (Map.Entry<String, TreeElement> entry : secondDict.entrySet()) {
            if (testVec.get(featIndex).equals(entry.getKey())) {
                if (entry.getValue() instanceof Klass) {
                    return ((Klass)entry.getValue()).getKlass();
                } else if (entry.getValue() instanceof Tree) {
                    return classfy((Tree) entry.getValue(), featLabels, testVec);
                }
            }
        }

        throw new IllegalStateException();
    }

    private BigDecimal log2(BigDecimal n) {
        return BigDecimal.valueOf(Math.log(n.doubleValue()) / Math.log(2));
    }

}
