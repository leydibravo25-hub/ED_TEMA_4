package com.leydisystems.menuarboles.classes;

import java.util.*;

public class BinaryTree {

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
        return find(root, value);
    }

    private boolean find(Node node, int value) {
        if (node == null) {
            return false;
        }

        if (node.value == value) {
            return true;
        }

        return find(node.left, value) || find(node.right, value);
    }

    public void insert(int value) {
        if (root == null) {
            root = new Node(value);
            return;
        }

        Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Node current = queue.remove();

            if (current.left == null) {
                current.left = new Node(value);
                return;
            }

            queue.add(current.left);

            if (current.right == null) {
                current.right = new Node(value);
                return;
            }

            queue.add(current.right);
        }
    }

    public void clear() {
        root = null;
    }

    public List<Integer> preorder() {
        List<Integer> result = new ArrayList<>();
        preorder(root, result);
        return result;
    }

    private void preorder(Node node, List<Integer> result) {
        if (node != null) {
            result.add(node.value);
            preorder(node.left, result);
            preorder(node.right, result);
        }
    }

    public List<Integer> inorder() {
        List<Integer> result = new ArrayList<>();
        inorder(root, result);
        return result;
    }

    private void inorder(Node node, List<Integer> result) {
        if (node != null) {
            inorder(node.left, result);
            result.add(node.value);
            inorder(node.right, result);
        }
    }

    public List<Integer> postorder() {
        List<Integer> result = new ArrayList<>();
        postorder(root, result);
        return result;
    }

    private void postorder(Node node, List<Integer> result) {
        if (node != null) {
            postorder(node.left, result);
            postorder(node.right, result);
            result.add(node.value);
        }
    }

    public List<Integer> levels() {
        List<Integer> result = new ArrayList<>();

        if (root != null) {
            Queue<Node> queue = new LinkedList<>();
            queue.add(root);

            while (!queue.isEmpty()) {
                Node node = queue.remove();
                result.add(node.value);

                if (node.left != null) {
                    queue.add(node.left);
                }

                if (node.right != null) {
                    queue.add(node.right);
                }
            }
        }

        return result;
    }
}