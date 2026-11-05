
package com.leydisystems.menuarboles.classes;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

public class TrieTree {

    private final Node root;

    public TrieTree() {
        root = new Node();
    }

    public static class Node {
        public Map<Character, Node> children;
        public boolean isEndOfWord;

        public Node() {
            children = new TreeMap<>();
            isEndOfWord = false;
        }
    }

    public Node getRoot() {
        return root;
    }

    public void addWord(String word) {
        if (word == null || word.trim().isEmpty()) {
            return;
        }

        String text = word.trim().toLowerCase();
        Node current = root;

        for (int i = 0; i < text.length(); i++) {
            char letter = text.charAt(i);

            if (!current.children.containsKey(letter)) {
                current.children.put(letter, new Node());
            }

            current = current.children.get(letter);
        }

        current.isEndOfWord = true;
    }

    public boolean searchWord(String word) {
        if (word == null || word.trim().isEmpty()) {
            return false;
        }

        Node current = root;
        String text = word.trim().toLowerCase();

        for (int i = 0; i < text.length(); i++) {
            char letter = text.charAt(i);

            if (!current.children.containsKey(letter)) {
                return false;
            }

            current = current.children.get(letter);
        }

        return current.isEndOfWord;
    }

    public List<String> getWords() {
        List<String> words = new ArrayList<>();
        collectWords(root, "", words);
        return words;
    }

    private void collectWords(
            Node node,
            String prefix,
            List<String> words
    ) {
        if (node.isEndOfWord) {
            words.add(prefix);
        }

        for (Map.Entry<Character, Node> entry
                : node.children.entrySet()) {
            collectWords(
                    entry.getValue(),
                    prefix + entry.getKey(),
                    words
            );
        }
    }

    // Preorder: visit the current node before its children.
    public List<String> getPreorder() {
        List<String> result = new ArrayList<>();
        preorder(root, "Raíz", result);
        return result;
    }

    private void preorder(
            Node node,
            String label,
            List<String> result
    ) {
        result.add(label);

        for (Map.Entry<Character, Node> entry
                : node.children.entrySet()) {
            preorder(
                    entry.getValue(),
                    String.valueOf(entry.getKey()),
                    result
            );
        }
    }

    // Postorder: visit the children before the current node.
    public List<String> getPostorder() {
        List<String> result = new ArrayList<>();
        postorder(root, "Raíz", result);
        return result;
    }

    private void postorder(
            Node node,
            String label,
            List<String> result
    ) {
        for (Map.Entry<Character, Node> entry
                : node.children.entrySet()) {
            postorder(
                    entry.getValue(),
                    String.valueOf(entry.getKey()),
                    result
            );
        }

        result.add(label);
    }

    // In a Trie, inorder is defined here as a traversal
    // between the first child and the remaining children.
    public List<String> getInorder() {
        List<String> result = new ArrayList<>();
        inorder(root, "Raíz", result);
        return result;
    }

    private void inorder(
            Node node,
            String label,
            List<String> result
    ) {
        List<Map.Entry<Character, Node>> children =
                new ArrayList<>(node.children.entrySet());

        if (!children.isEmpty()) {
            Map.Entry<Character, Node> firstChild = children.get(0);

            inorder(
                    firstChild.getValue(),
                    String.valueOf(firstChild.getKey()),
                    result
            );
        }

        result.add(label);

        for (int i = 1; i < children.size(); i++) {
            Map.Entry<Character, Node> entry = children.get(i);

            inorder(
                    entry.getValue(),
                    String.valueOf(entry.getKey()),
                    result
            );
        }
    }

    // Level-order: visit the nodes one level at a time.
    public List<String> getLevels() {
        List<String> result = new ArrayList<>();
        Queue<Node> queue = new LinkedList<>();
        Queue<String> labels = new LinkedList<>();

        queue.add(root);
        labels.add("Raíz");

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            String label = labels.poll();

            result.add(label);

            for (Map.Entry<Character, Node> entry
                    : current.children.entrySet()) {
                queue.add(entry.getValue());
                labels.add(String.valueOf(entry.getKey()));
            }
        }

        return result;
    }

    public boolean isEmpty() {
        return root.children.isEmpty();
    }

    public void clear() {
        root.children.clear();
        root.isEndOfWord = false;
    }
}
