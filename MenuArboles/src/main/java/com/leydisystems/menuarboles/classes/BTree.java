package com.leydisystems.menuarboles.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class BTree {

    private final TreeSet<Integer> keys = new TreeSet<>();

    public boolean search(int value) {
        return keys.contains(value);
    }

    public void insert(int value) {
        keys.add(value);
    }

    public List<Integer> values() {
        return new ArrayList<>(keys);
    }

    public void clear() {
        keys.clear();
    }
}