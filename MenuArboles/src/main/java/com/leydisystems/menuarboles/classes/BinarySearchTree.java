package com.leydisystems.menuarboles.classes;

import java.util.ArrayList;
import java.util.List;

public class BinarySearchTree {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    private Node root;

    public Node getRoot() {
        return root;
    }

    public boolean search(int value) {
        Node current = root;

        while (current != null) {
            if (current.value == value) {
                return true;
            }

            if (value < current.value) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        return false;
    }

    public void insert(int value) {
        root = insertNode(root, value);
    }

    private Node insertNode(Node node, int value) {
        if (node == null) {
            return new Node(value);
        }

        if (value < node.value) {
            node.left = insertNode(node.left, value);
        } else if (value > node.value) {
            node.right = insertNode(node.right, value);
        }

        return node;
    }

    public void clear() {
        root = null;
    }

    public List<Integer> inorder() {
        List<Integer> result = new ArrayList<>();
        traverse(root, result);
        return result;
    }

    private void traverse(Node node, List<Integer> result) {
        if (node != null) {
            traverse(node.left, result);
            result.add(node.value);
            traverse(node.right, result);
        }
    }
}