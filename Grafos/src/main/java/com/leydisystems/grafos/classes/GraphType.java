
package com.leydisystems.grafos.classes;

public enum GraphType {

    UNDIRECTED("Grafo no dirigido"),
    DIRECTED("Grafo dirigido"),
    WEIGHTED("Grafo ponderado"),
    COMPLETE("Grafo completo"),
    CYCLIC("Grafo cíclico");

    private final String title;

    GraphType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
