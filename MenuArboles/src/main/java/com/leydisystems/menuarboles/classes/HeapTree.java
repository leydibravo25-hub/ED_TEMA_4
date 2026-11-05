package com.leydisystems.menuarboles.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class HeapTree {

    private final PriorityQueue<Integer> heap =
            new PriorityQueue<>();

    public boolean search(int value) {
        return heap.contains(value);
    }

    public void insert(int value) {
        heap.add(value);
    }

    public Integer extractMin() {
        return heap.poll();
    }

    public Integer min() {
        return heap.peek();
    }

    public List<Integer> values() {
        return new ArrayList<>(heap);
    }

    public void clear() {
        heap.clear();
    }
}