
package com.leydisystems.grafos.classes;

import java.util.*;

public class Graph {

    private final GraphType type;

    private final Map<String, List<Edge>> adjacency =
            new LinkedHashMap<>();

    public Graph(GraphType type) {
        this.type = type;
    }

    public boolean addVertex(String vertex) {

        if (vertex == null || vertex.trim().isEmpty()) {
            return false;
        }

        vertex = vertex.trim();

        if (adjacency.containsKey(vertex)) {
            return false;
        }

        adjacency.put(vertex, new ArrayList<>());

        if (type == GraphType.COMPLETE) {
            for (String other : adjacency.keySet()) {
                if (!other.equals(vertex)) {
                    adjacency.get(vertex).add(new Edge(vertex, other, 1));
                    adjacency.get(other).add(new Edge(other, vertex, 1));
                }
            }
        }

        if (type == GraphType.CYCLIC && adjacency.size() > 1) {
            List<String> vertices =
                    new ArrayList<>(adjacency.keySet());

            String previous = vertices.get(vertices.size() - 2);

            addConnection(previous, vertex, 1);

            if (vertices.size() > 2) {
                addConnection(vertex, vertices.get(0), 1);
            }
        }

        return true;
    }

    public boolean addEdge(String from, String to, double weight) {

        if (!adjacency.containsKey(from)
                || !adjacency.containsKey(to)
                || from.equals(to)) {
            return false;
        }

        if (type == GraphType.COMPLETE) {
            return false;
        }

        if (hasEdge(from, to)) {
            return false;
        }

        addConnection(from, to, weight);

        if (type != GraphType.DIRECTED) {
            addConnection(to, from, weight);
        }

        return true;
    }

    private void addConnection(
            String from,
            String to,
            double weight) {

        adjacency.get(from).add(new Edge(from, to, weight));
    }

    public boolean removeVertex(String vertex) {

        if (!adjacency.containsKey(vertex)) {
            return false;
        }

        adjacency.remove(vertex);

        for (List<Edge> edges : adjacency.values()) {
            edges.removeIf(edge -> edge.getTo().equals(vertex));
        }

        return true;
    }

    public boolean containsVertex(String vertex) {
        return adjacency.containsKey(vertex);
    }

    public boolean hasEdge(String from, String to) {

        if (!adjacency.containsKey(from)) {
            return false;
        }

        for (Edge edge : adjacency.get(from)) {
            if (edge.getTo().equals(to)) {
                return true;
            }
        }

        return false;
    }

    public Set<String> getVertices() {
        return Collections.unmodifiableSet(adjacency.keySet());
    }

    public Map<String, List<Edge>> getAdjacency() {
        return Collections.unmodifiableMap(adjacency);
    }

    public GraphType getType() {
        return type;
    }

    public List<String> bfs(String start) {

        List<String> result = new ArrayList<>();

        if (!adjacency.containsKey(start)) {
            return result;
        }

        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {

            String current = queue.poll();
            result.add(current);

            for (Edge edge : adjacency.get(current)) {

                String neighbor = edge.getTo();

                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }

        return result;
    }

    public List<String> dfs(String start) {

        List<String> result = new ArrayList<>();

        if (!adjacency.containsKey(start)) {
            return result;
        }

        Set<String> visited = new HashSet<>();

        dfsRecursive(start, visited, result);

        return result;
    }

    private void dfsRecursive(
            String current,
            Set<String> visited,
            List<String> result) {

        visited.add(current);
        result.add(current);

        for (Edge edge : adjacency.get(current)) {

            String neighbor = edge.getTo();

            if (!visited.contains(neighbor)) {
                dfsRecursive(neighbor, visited, result);
            }
        }
    }

    public void clear() {
        adjacency.clear();
    }

    public String describe() {

        StringBuilder result = new StringBuilder();

        result.append(type.getTitle()).append("\n\n");

        for (String vertex : adjacency.keySet()) {

            result.append(vertex).append(" -> ");

            for (Edge edge : adjacency.get(vertex)) {
                result.append(edge.getTo());

                if (type == GraphType.WEIGHTED) {
                    result.append(" (").append(edge.getWeight()).append(")");
                }

                result.append("   ");
            }

            result.append("\n");
        }

        return result.toString();
    }
}
