package com.personal.oyl.trees;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public enum FileReader {
    INSTANCE;

    public List<String> read(String filename) throws IOException {
        List<String> rlt = new LinkedList<>();

        try (
             java.io.FileReader fr = new java.io.FileReader(filename);
             BufferedReader br = new BufferedReader(fr)) {

            String line = null;
            while ((line = br.readLine()) != null) {
                rlt.add(line.trim());
            }

            return rlt;
        }
    }
}
