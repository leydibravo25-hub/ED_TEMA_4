
package com.leydisystems.grafos.controller;

import com.leydisystems.grafos.classes.GraphType;

public class UndirectedGraphController extends GraphController {

    @Override
    protected GraphType getGraphType() {
        return GraphType.UNDIRECTED;
    }
}
