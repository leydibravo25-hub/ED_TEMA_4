
package com.leydisystems.grafos.controller;

import com.leydisystems.grafos.classes.GraphType;

public class DirectedGraphController extends GraphController {

    @Override
    protected GraphType getGraphType() {
        return GraphType.DIRECTED;
    }
}
