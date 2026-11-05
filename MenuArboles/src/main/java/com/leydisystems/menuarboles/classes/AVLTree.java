package com.leydisystems.menuarboles.classes;

public class AVLTree {

    public static class Node {
        public int value;
        public int height = 1;
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

    private int height(Node node) {
        return node == null ? 0 : node.height;
    }

    private int balance(Node node) {
        return node == null ? 0 :
                height(node.left) - height(node.right);
    }

    private Node rotateRight(Node node) {
        Node newRoot = node.left;
        Node temporary = newRoot.right;

        newRoot.right = node;
        node.left = temporary;

        node.height = 1 + Math.max(
                height(node.left), height(node.right));

        newRoot.height = 1 + Math.max(
                height(newRoot.left), height(newRoot.right));

        return newRoot;
    }

    private Node rotateLeft(Node node) {
        Node newRoot = node.right;
        Node temporary = newRoot.left;

        newRoot.left = node;
        node.right = temporary;

        node.height = 1 + Math.max(
                height(node.left), height(node.right));

        newRoot.height = 1 + Math.max(
                height(newRoot.left), height(newRoot.right));

        return newRoot;
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
        } else {
            return node;
        }

        node.height = 1 + Math.max(
                height(node.left), height(node.right));

        int balanceValue = balance(node);

        if (balanceValue > 1 && value < node.left.value) {
            return rotateRight(node);
        }

        if (balanceValue < -1 && value > node.right.value) {
            return rotateLeft(node);
        }

        if (balanceValue > 1 && value > node.left.value) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        if (balanceValue < -1 && value < node.right.value) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    public boolean search(int value) {
        Node current = root;

        while (current != null) {
            if (current.value == value) {
                return true;
            }

            current = value < current.value
                    ? current.left : current.right;
        }

        return false;
    }

    public void clear() {
        root = null;
    }
}